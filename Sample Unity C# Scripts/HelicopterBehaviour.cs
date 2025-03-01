using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HelicopterBehaviour : MonoBehaviour
{
    private string flyingDirection = "";
    private bool hasDroppedTrooper = false;
    public GameObject paratrooperPrefab;

    void Start()
    {
        if(this.transform.position.x>8.0f){
            flyingDirection = "left";
        }
        if(this.transform.position.x<-8.0f){
            flyingDirection = "right";
        }
    }


    void Update()
    {
        if(!GameManager.Instance.isGameOver){
        if(flyingDirection.Equals("right")){
            this.transform.position = new Vector3 ((this.transform.position.x + 0.025f), this.transform.position.y, this.transform.position.z) ;
            if(this.transform.position.x>10.0f){
                Destroy(gameObject);
            }
        }
        if(flyingDirection.Equals("left")){
            this.transform.position = new Vector3 ((this.transform.position.x - 0.025f), this.transform.position.y, this.transform.position.z) ;
            if(this.transform.position.x<-10.0f){
                Destroy(gameObject);
            }
        }
        if(!TrooperManager.Instance.FourTroopersLanded()){
            SpawnParatrooper();
        }
        }
    }

    private void SpawnParatrooper()
    {
        if((Random.Range(1, 100)%97==0) && !hasDroppedTrooper)
        {
            if(!((this.transform.position.x > -5.0f) && (this.transform.position.x < 5.0f))){
                if((this.transform.position.x > -8.0f) && (this.transform.position.x < 8.0f)){
                    Instantiate(paratrooperPrefab, new Vector3((this.transform.position.x - (this.transform.position.x%1.0f)), (this.transform.position.y-0.75f), this.transform.position.z) , Quaternion.identity);
                    hasDroppedTrooper=true;
                    TrooperManager.Instance.AddParatrooperInstance();
                }
            }
        }
    }
}
