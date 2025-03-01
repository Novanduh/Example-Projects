using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrooperManager : MonoBehaviour
{
    public static TrooperManager Instance { get; private set; }
    private int numberOfTroopers, numberOfGroundedTroopers;

    private List<GameObject> listOfTroopersRight = new List<GameObject>();
    private List<GameObject> listOfTroopersLeft = new List<GameObject>();

    public GameObject rightPyramid, leftPyramid;

    private void Awake() 
    { 
        if (Instance != null && Instance != this) 
        { 
            Destroy(this); 
        } 
        else 
        { 
            Instance = this; 
        } 
        numberOfTroopers=0;
        numberOfGroundedTroopers=0;
    }

    //PARATROOPER COUNTING FUNCTIONS//
    public void AddParatrooperInstance(){
        ++numberOfTroopers;
    }

    public void RemoveParatrooperInstance(){
        --numberOfTroopers;
    }

    public void AddGroundedTrooper(string sideOfLanding, GameObject trooper){
        if(sideOfLanding.Equals("left")){
            listOfTroopersLeft.Add(trooper);
        }
        else{
            listOfTroopersRight.Add(trooper);
        }
        ++numberOfGroundedTroopers;
    }

    public bool FourTroopersLanded(){
        return ((listOfTroopersRight.Count>=4)||(listOfTroopersLeft.Count>=4));
    }
    /////////////////
    
    //CLIMBING FUNCTIONS//
    public void SortTroop(){
        //if(numberOfGroundedTroopers==numberOfTroopers){
        if (listOfTroopersRight.Count>=4){
            //SortTroopHelper(listOfTroopersRight);
            MoveTroop("right");
        }
        else if(listOfTroopersLeft.Count>=4){
            //SortTroopHelper(listOfTroopersLeft);
            MoveTroop("left");
        }
        //}
    }

    /*private void SortTroopHelper(List<GameObject> troopList){
        for(int i=0; i<(troopList.Count-1); i++){
            for (int j=0; j<(troopList.Count - i -1); j++){
                if(troopList[j].transform.position.x>troopList[j+1].transform.position.x){
                    GameObject temp = troopList[j];
                    troopList[j]=troopList[j+1];
                    troopList[j+1]=temp;
                }
                else if(troopList[j].transform.position.x==troopList[j+1].transform.position.x){
                    if(troopList[j].transform.position.y>troopList[j+1].transform.position.y){
                        GameObject temp = troopList[j];
                        troopList[j]=troopList[j+1];
                        troopList[j+1]=temp;
                    }
                }
            }
        }
    }*/
    
    private void MoveTroop(string pyramidSide){
        if(pyramidSide.Equals("right")){
            for(int i=0; i<4; i++)
                rightPyramid.GetComponent<ClimbPyramid>().AssemblePyramid(listOfTroopersRight[i], i);
        }
        else{
            for(int i=0; i<4; i++)
                leftPyramid.GetComponent<ClimbPyramid>().AssemblePyramid(listOfTroopersLeft[i], i);
        }
    }
    /////////////////
}
