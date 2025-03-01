using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance { get; private set; }
    public GameObject turret, helicopterTopSpawn, helicopterBottomSpawn, helicopterPrefab, gameOverText;
    private int score;
    public TMP_Text scorePoints;
    public bool isGameOver=false;
    

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
        InitialiseScore();
    }

    void Update(){
        if(!isGameOver){
            MoveTurret();
            SpawnHelicopter();
            TrooperManager.Instance.SortTroop();
        }
    }

    //TURRET CONTROL//
    private void MoveTurret(){
        turret.GetComponent<TurretController>().MoveTurret();
    }

    public float GetTurrelAngle(){
        return turret.GetComponent<TurretController>().GetPivotAngle();
    }

    public void SetTurretDirection(string direction){
        turret.GetComponent<TurretController>().SetTurretDirection(direction);
    }

    public void SpawnBullet(){
        turret.GetComponent<TurretController>().SpawnBullet();
    }
    //////////////////

    public void SpawnHelicopter(){
        if((Random.Range(1, 100)%97==0)){
            if(Random.Range(1,9)%3==0)
                Instantiate(helicopterPrefab, helicopterBottomSpawn.transform.position, Quaternion.identity);
            else
                Instantiate(helicopterPrefab, helicopterTopSpawn.transform.position, Quaternion.identity);
        }
    }

    private void InitialiseScore(){
        score = 0;
    }

    public void SetScore(int addScore){
        score += addScore;
        if(score<0){
            score=0;
            return;
        }
        scorePoints.text = score.ToString();
    }

    public void GameOver(){
        isGameOver=true;
        //Destroy(turret);
        gameOverText.SetActive(true);
        Time.timeScale=0;
    }
}
