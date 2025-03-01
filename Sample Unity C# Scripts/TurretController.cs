using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TurretController : MonoBehaviour
{
    private string turretDirection="";
    private float turretAngle=80.0f;
    public GameObject turretPivot, bulletSpawnPoint, bulletPrefab;

    public void MoveTurret(){
        if (turretDirection.Equals("counter-clockwise") && (turretAngle<160.0f)){
            turretAngle+=1.0f;
            turretPivot.transform.Rotate(0.0f, 0.0f, 1.0f, Space.Self);
        }
        else if (turretDirection.Equals("clockwise") && (turretAngle>0.0f)){
            turretAngle-=1.0f;
            turretPivot.transform.Rotate(0.0f, 0.0f, -1.0f, Space.Self);
        }
        else{
            return;
        }
    }

    public void SetTurretDirection(string direction){
        turretDirection=direction;
    }

    public float GetPivotAngle(){
        return (turretAngle-80.0f);
    }

    public void SpawnBullet(){
        Instantiate(bulletPrefab, bulletSpawnPoint.transform.position, Quaternion.identity);
        GameManager.Instance.SetScore(-1);
    }
}
