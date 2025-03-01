using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BulletBehaviour : MonoBehaviour
{
    private float bulletAngle;
    void Start(){
        bulletAngle = GameManager.Instance.GetTurrelAngle() * Mathf.PI / 180.0f;
    }
    void Update()
    {
        if(!GameManager.Instance.isGameOver){
        if((this.transform.position.x>8.0f)||(this.transform.position.x<-8.0f)||(this.transform.position.y>5.0f))
            Destroy(gameObject);
        this.transform.position = new Vector3 ((this.transform.position.x - (0.05f*Mathf.Sin(bulletAngle))), (this.transform.position.y + (0.05f*Mathf.Cos(bulletAngle))), this.transform.position.z) ;
        }
    }

    void OnCollisionEnter2D(Collision2D other)
    {
        if(other.gameObject.CompareTag("Helicopter")){
            Destroy(other.gameObject);
            Destroy(this.gameObject);
            GameManager.Instance.SetScore(10);
        }
        else if (other.gameObject.CompareTag("Paratrooper")){
            Destroy(other.gameObject);
            Destroy(this.gameObject);
            TrooperManager.Instance.RemoveParatrooperInstance();
            GameManager.Instance.SetScore(5);
        }
    }
}
