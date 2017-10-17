using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Move : MonoBehaviour {

    private Rigidbody player_position;
    private ShareState variables;

    // Use this for initialization
    void Start () {
        player_position = GetComponent<Rigidbody>();
        GameObject g = GameObject.Find("GameData");
        if (g != null)
        {
            variables = g.GetComponent<ShareState>();
        }
	}
	
	// Update is called once per frame
	private void FixedUpdate () {
        int a_x = 1;
        int a_y = 1;
        if(variables != null)
        {
            a_x = variables.get("x");
            a_y = variables.get("y");
        }
        float x = Input.acceleration.x*5*a_x;
        float y = Input.acceleration.y*5*a_y;
        Vector3 new_pos = new Vector3(x, 0f, y);
        player_position.AddForce(new_pos*Time.deltaTime, ForceMode.Impulse);
    }

    void updatePositon(float move_x, float move_y)
    {
        
    }
}
