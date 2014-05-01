package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.QuestionModel.QuestionType;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.PatientExercise;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;
import edu.wpi.smartcoach.service.PatientExerciseService;

public class MotivationProblemSolver implements ProblemSolver {

	private static final String TAG = MotivationProblemSolver.class
			.getSimpleName();

	private static final int EXERCISE_QUESTION_LOCATION = 0;
	private static final int EXERCISE_QUESTION_TIME = 1;
	private static final int EXERCISE_QUESTION_LIKE = 2;

	private static final int YES = 1;
	private static final int NO = 0;

	private static final QuestionModel[] EXERCISE_QUESTIONS = {

			new QuestionModel("location", "Location",
					"Where did you try <exercise>", ExerciseLocationService
							.getInstance().getAllDataFromTable(),
					QuestionType.MULTIPLE,1,QuestionModel.NO_LIMIT),

			new QuestionModel("time", "Time", "When did you try <exercise>",
					ExerciseTimeService.getInstance().getAllDataFromTable(),
					QuestionType.MULTIPLE,1,QuestionModel.NO_LIMIT),

			new QuestionModel("like", "Liked",
					"Did you enjoy <exercise> at <location> in the <time>?",
					new ArrayList<OptionModel>() {
						{
							add(new SimpleOption(YES, "Yes"));
							add(new SimpleOption(NO, "No"));
						}
					}, QuestionType.MULTIPLE, 1, QuestionModel.NO_LIMIT) };

	private boolean exercisesSubmitted;
	private ArrayList<PatientExercise> patientExerciseList = null;

	private int exerciseIndex;

	private int exerciseQuestionIndex;

	public MotivationProblemSolver() {
		exercisesSubmitted = false;
		patientExerciseList = new ArrayList<PatientExercise>();

		exerciseIndex = 0;
		exerciseQuestionIndex = 0;

	}

	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next = null;
		if (hasNextQuestion()) {
			if (!exercisesSubmitted) {
				next = new QuestionModel("exercises", "Exercises",
						"Which exercises did you try to do?", ExerciseService.getInstance().getAllDataFromTable(),
						QuestionType.MULTIPLE);

			} else {
				PatientExercise current = patientExerciseList
						.get(exerciseIndex);
				String exerciseNameString = ExerciseService.getInstance()
						.getExerciseName(current.getExerciseID());
				switch (exerciseQuestionIndex) {
				case EXERCISE_QUESTION_LOCATION:
					next = EXERCISE_QUESTIONS[EXERCISE_QUESTION_LOCATION]
							.clone();
					next.setPrompt(next.getPrompt().replace("<exercise>",
							exerciseNameString));
					next.setOptions(getLocationOptions());
					
					break;
				case EXERCISE_QUESTION_TIME:
					next = EXERCISE_QUESTIONS[EXERCISE_QUESTION_TIME].clone();
					next.setPrompt(next.getPrompt().replace("<exercise>",
							exerciseNameString));
					break;
				case EXERCISE_QUESTION_LIKE:
					String exerciseLocation = ExerciseLocationService
							.getInstance().getSpecificLocation(
									current.getExerciseLocationID());
					String exerciseTime = ExerciseTimeService.getInstance()
							.getExerciseTime(current.getExerciseTimeID());
					next = EXERCISE_QUESTIONS[EXERCISE_QUESTION_LIKE].clone();
					String prompt = next.getPrompt()
							.replace("<exercise>", exerciseNameString)
							.replace("<location>", exerciseLocation)
							.replace("<time>", exerciseTime);
					next.setPrompt(prompt);

				}
			}
		}

		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		if (!exercisesSubmitted) {
			exercisesSubmitted = true;
			List<Option> responseList = response.getSelectedResponses();
			for (Option op : responseList) {
				int exerciseID = ((Exercise) op.getModel()).getId();
				patientExerciseList.add(new PatientExercise(1, exerciseID, -1,
						-1, false));
			}
		} else {
			PatientExercise current = patientExerciseList.get(exerciseIndex);
			switch (exerciseQuestionIndex) {
			case EXERCISE_QUESTION_LOCATION:
				String locationName = response.getSelectedResponses().get(0)
						.getModel().getName();
				int locationID = ExerciseLocationService.getInstance()
						.getExerciseLocationID(locationName);
				current.setExerciseLocationID(locationID);
				break;
			case EXERCISE_QUESTION_TIME:
				String time = response.getSelectedResponses().get(0).getModel()
						.getName();
				int timeID = ExerciseTimeService.getInstance()
						.getExerciseTimeID(time);
				current.setExerciseTimeID(timeID);
				break;
			case EXERCISE_QUESTION_LIKE:
				boolean like = response.getSelectedResponses().get(0)
						.getModel().getId() == YES;
				current.setLiked(like);

			}

			exerciseQuestionIndex++;
			if (exerciseQuestionIndex == EXERCISE_QUESTIONS.length) {
				exerciseQuestionIndex = 0;
				exerciseIndex++;
			}
			Log.d(TAG, "index " + exerciseIndex + ":" + exerciseQuestionIndex);
		}
	}
	
	private List<OptionModel> getLocationOptions(){
		PatientExercise current = patientExerciseList.get(exerciseIndex);
		List<Integer> locationIds = ExerciseToLocationService.getInstance().getLocationListByExercise(current.getExerciseID());
		return ExerciseLocationService.getInstance().getLocations(locationIds);
	}

	@Override
	public boolean hasNextQuestion() {
		boolean hasNext = !exercisesSubmitted
				|| exerciseIndex < patientExerciseList.size();
		Log.d(TAG, "hasNextQuestion " + hasNext);
		return hasNext;
	}

	@Override
	public QuestionModel getSolution() {
		ArrayList<OptionModel> solutions = new ArrayList<OptionModel>();
		int id = 0;
		for (PatientExercise mPatientExercise : patientExerciseList) {
			id++;
			String exerciseNameString = ExerciseService.getInstance()
					.getExerciseName(mPatientExercise.getExerciseID());
			String exerciseLocation = ExerciseLocationService.getInstance()
					.getSpecificLocation(
							mPatientExercise.getExerciseLocationID());
			String exerciseTime = ExerciseTimeService.getInstance()
					.getExerciseTime(mPatientExercise.getExerciseTimeID());
			String result = String.format("You %slike %s at %s in the %s",
					mPatientExercise.isLiked() ? "" : "do not ",
					exerciseNameString, exerciseLocation, exerciseTime);
			solutions.add(new SimpleOption(id, result));
			//insert solution into db
			PatientExerciseService.getInstance().addPatientExercise(mPatientExercise);
		}
		return new QuestionModel("results", "Results", "Here are the results.",
				solutions, QuestionType.SINGLE);

	}

}
