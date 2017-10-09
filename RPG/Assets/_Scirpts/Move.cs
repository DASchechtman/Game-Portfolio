using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Move : MonoBehaviour {

    private Rigidbody player_position;

    private void Awake()
    {
        Application.targetFrameRate = 600;
    }

    // Use this for initialization
    void Start () {
        player_position = GetComponent<Rigidbody>();
	}
	
	// Update is called once per frame
	private void FixedUpdate () {
        Vector3 new_pos = new Vector3(Input.GetAxis("Horizontal"), 0f, Input.GetAxis("Vertical"));
        player_position.AddForce(new_pos*Time.deltaTime, ForceMode.Impulse);
    }

    void updatePositon(float move_x, float move_y)
    {
        
    }
}
