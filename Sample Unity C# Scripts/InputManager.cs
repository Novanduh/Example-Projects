using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class InputManager : MonoBehaviour
{
    void Update()
    {
      if(!GameManager.Instance.isGameOver){
        if(Input.GetKeyDown(KeyCode.UpArrow)){
           GameManager.Instance.SetTurretDirection("stationary");
           GameManager.Instance.SpawnBullet();
        }
        if(Input.GetKeyDown(KeyCode.LeftArrow)){
           GameManager.Instance.SetTurretDirection("counter-clockwise");
        }
        if(Input.GetKeyDown(KeyCode.RightArrow)){
           GameManager.Instance.SetTurretDirection("clockwise");
        }
      }
    }
}
