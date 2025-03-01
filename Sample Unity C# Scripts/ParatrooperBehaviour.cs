using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ParatrooperBehaviour : MonoBehaviour
{
    public GameObject parachute;

    void OnCollisionEnter2D(Collision2D other)
    {
        if(other.gameObject.CompareTag("Ground")){
            Destroy(this.GetComponent<Rigidbody2D>());
            Destroy(parachute);
            if((this.transform.position.x <- 4.0f) && !(gameObject.CompareTag("Ground"))){
                TrooperManager.Instance.AddGroundedTrooper("left", this.gameObject);
            }
            else if((this.transform.position.x > 4.0f) && !(gameObject.CompareTag("Ground"))){
                TrooperManager.Instance.AddGroundedTrooper("right", this.gameObject);
            }
            gameObject.tag="Ground";
        }
    }
}
