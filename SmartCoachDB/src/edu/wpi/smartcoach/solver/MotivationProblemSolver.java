package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import edu.wpi.smartcoach.model.Option;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseStateService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;

public class MotivationProblemSolver implements ProblemSolver {

	private static final String TAG = MotivationProblemSolver.class
			.getSimpleName();

	private static final int EXERCISE_QUESTION_LOCATION = 0;
	private static final int EXERCISE_QUESTION_TIME = 1;
	private static final int EXERCISE_QUESTION_LIKE = 2;

	private static final int YES = 1;
	private static final int NO = 0;

	private static final QuestionModel[] EXERCISE_QUESTIONS = {

			new OptionQuestionModel("location", "Location",
					"Where do you <exercise>", ExerciseLocationService
							.getInstance().getAllDataFromTable(),
					QuestionType.MULTIPLE,1,OptionQuestionModel.NO_LIMIT),

			new OptionQuestionModel("time", "Time", "When do you <exercise>",
					ExerciseTimeService.getInstance().getAllDataFromTable(),
					QuestionType.MULTIPLE,1,OptionQuestionModel.NO_LIMIT),
			new OptionQuestionModel("frequency", "Frequency", "On average, how many days do you <exercise> in a week?",
					new ArrayList<OptionModel>(){{
						add(new SimpleOption(0, "Less than once per week"));
						add(new SimpleOption(1, "Once per week"));
						for(int i = 2; i < 7; i++){
							add(new SimpleOption(i, i + "times per week"));
						}
						add(new SimpleOption(7, "7 times per week (daily)"));
					}},
					QuestionType.SINGLE),
					
			new TimeQuestionModel("duration", "Duration",
					"On average, how much time do you spend <exercise> each day?"),
					

			new OptionQuestionModel("like", "Liked",
					"Did you enjoy <exercise> at <location> in the <time>?",
					new ArrayList<OptionModel>() {
						{
							add(new SimpleOption(YES, "Yes"));
							add(new SimpleOption(NO, "No"));
						}
					}, QuestionType.MULTIPLE, 1, OptionQuestionModel.NO_LIMIT) };

	private boolean exercisesSubmitted;
	private ArrayList<ExerciseState> stateList = null;

	private int exerciseIndex;

	private int exerciseQuestionIndex;

	public MotivationProblemSolver() {
		exercisesSubmitted = false;
		stateList = new ArrayList<ExerciseState>();

		exerciseIndex = 0;
		exerciseQuestionIndex = 0;

	}

	@Override
	public OptionQuestionModel getNextQuestion() {
		OptionQuestionModel next = null;
		if (hasNextQuestion()) {
			if (!exercisesSubmitted) {
				ArrayList<SimpleOption> exerciseOptions = new ArrayList<SimpleOption>();
				List<Exercise> exerciseList = ExerciseService.getInstance().getAllDataFromTable();
				for(Exercise e:exerciseList){
					exerciseOptions.add(new SimpleOption(e.getId(), e));
				}
				next = new OptionQuestionModel("exercises", "Exercises",
						"Which exercises did you try to do?", exerciseOptions,
						QuestionType.MULTIPLE);

			} else {
				ExerciseState current = stateList
						.get(exerciseIndex);
				String exerciseNameString = current.getExercise().getName();
				switch (exerciseQuestionIndex) {
				case EXERCISE_QUESTION_LOCATION:
					next = (OptionQuestionModel) EXERCISE_QUESTIONS[EXERCISE_QUESTION_LOCATION]
							.clone();
					next.setPrompt(next.getPrompt().replace("<exercise>",
							exerciseNameString));
					next.setOptions(getLocationOptions());
					
					break;
				case EXERCISE_QUESTION_TIME:
					next = (OptionQuestionModel) EXERCISE_QUESTIONS[EXERCISE_QUESTION_TIME].clone();
					next.setPrompt(next.getPrompt().replace("<exercise>",
							exerciseNameString));
					break;
				case EXERCISE_QUESTION_LIKE:
					String exerciseLocation = current.getLocation().getSpecificLocation().toLowerCase();
					String exerciseTime = current.getTime().getTime().toLowerCase();
					next = (OptionQuestionModel) EXERCISE_QUESTIONS[EXERCISE_QUESTION_LIKE].clone();
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
	public void submitResponse(OptionQuestionModel response) {
		if (!exercisesSubmitted) {
			exercisesSubmitted = true;
			List<Option> responseList = response.getSelectedResponses();
			for (Option op : responseList) {
				int exerciseID = ((Exercise) op.getModel()).getId();
				stateList.add(new ExerciseState(0));
			}
		} else {
			ExerciseState current = stateList.get(exerciseIndex);
			switch (exerciseQuestionIndex) {
			case EXERCISE_QUESTION_LOCATION:
				ExerciseLocation location = (ExerciseLocation)response.getSelectedResponses().get(0)
						.getModel().getValue();
				current.setLocation(location);
				break;
			case EXERCISE_QUESTION_TIME:
				ExerciseTime time = (ExerciseTime)response.getSelectedResponses().get(0).getModel().getValue();
				current.setTime(time);
				break;
			case EXERCISE_QUESTION_LIKE:
				boolean like = response.getSelectedResponses().get(0)
						.getModel().getId() == YES;
				current.setExerciseLiked(like);

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
		ExerciseState current = stateList.get(exerciseIndex);
		List<Integer> locationIds = ExerciseToLocationService.getInstance().getLocationListByExercise(current.getExercise().getId());
		return ExerciseLocationService.getInstance().getLocations(locationIds);
	}

	@Override
	public boolean hasNextQuestion() {
		boolean hasNext = !exercisesSubmitted
				|| exerciseIndex < stateList.size();
		Log.d(TAG, "hasNextQuestion " + hasNext);
		return hasNext;
	}

	@Override
	public OptionQuestionModel getSolution() {
		ArrayList<OptionModel> solutions = new ArrayList<OptionModel>();
		int id = 0;
		for (ExerciseState mPatientExercise : stateList) {
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
			ExerciseStateService.getInstance().addPatientExercise(mPatientExercise);
		}
		return new OptionQuestionModel("results", "Results", "Here are the results.",
				solutions, QuestionType.SINGLE);

	}

}
