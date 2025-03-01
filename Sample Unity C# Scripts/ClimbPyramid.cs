using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ClimbPyramid : MonoBehaviour
{
    public List<GameObject> firstParatrooperRoute;
    public List<GameObject> secondParatrooperRoute;
    public List<GameObject> thirdParatrooperRoute;
    public List<GameObject> fourthParatrooperRoute;

    public void AssemblePyramid(GameObject paratrooper, int paratrooperPosition){
        switch (paratrooperPosition){
            case 1: {
                AssemblePyramidHelper(firstParatrooperRoute, paratrooper);
                break;
            }
            case 2: {
                AssemblePyramidHelper(secondParatrooperRoute, paratrooper);
                break;
            }
            case 3: {
                AssemblePyramidHelper(thirdParatrooperRoute, paratrooper);
                break;
            }
            default: {
                AssemblePyramidHelper(fourthParatrooperRoute, paratrooper);
                GameManager.Instance.GameOver();
                break;
            }
        }
    }

    private void AssemblePyramidHelper(List<GameObject> paratrooperRoute, GameObject paratrooper){
        foreach (GameObject pyramidPosition in paratrooperRoute){
            paratrooper.transform.position=pyramidPosition.transform.position;
        }
    }
}
