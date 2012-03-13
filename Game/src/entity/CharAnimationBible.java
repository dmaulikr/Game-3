package entity;

import java.util.ArrayList;
import entity.Job.jobList;
import displaymanager.GameboardRender.viewPoint;
import entity.Character.*;

public class CharAnimationBible {
	int nbPointsOfView = 4;
	int nbJobs = 3;
	int nbRaces = 3;
	int nbGenders = 3;
	int nsStances = 7;

	private Animation[][][][][] AnimationBible;

	public CharAnimationBible() {
		AnimationBible = new Animation[nbPointsOfView][nbJobs][nbRaces][nbGenders][nsStances];
	}

	public Animation getAnimation(viewPoint vp, jobList job, Race race, Gender gen, CharState state) {
		return AnimationBible[vp.ordinal()][job.ordinal()][race.ordinal()][gen.ordinal()][state.ordinal()];
	}

	public void CreateAll() {
		for (viewPoint vp : viewPoint.values()) {
			for (jobList job : jobList.values()) {
				for (Race race : Race.values()) {
					for (Gender gen : Gender.values()) {
						for (CharState state : CharState.values()) {
							AnimationBible[vp.ordinal()][job.ordinal()][race.ordinal()][gen.ordinal()][state.ordinal()] = new CharAnimation(vp, job, race, gen, state);
						}
					}
				}
			}
		}
	}

	public void LoadAll() {
		for (viewPoint vp : viewPoint.values()) {
			for (jobList job : jobList.values()) {
				for (Race race : Race.values()) {
					for (Gender gen : Gender.values()) {
						for (CharState state : CharState.values()) {
							AnimationBible[vp.ordinal()][job.ordinal()][race.ordinal()][gen.ordinal()][state.ordinal()].Load();
						}
					}
				}
			}
		}
	}

}
