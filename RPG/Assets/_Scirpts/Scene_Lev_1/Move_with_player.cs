using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Move_with_player : MonoBehaviour {


    public Transform player_transform;
    private Transform cam_transform;

	// Use this for initialization
	void Start () {
        cam_transform = GetComponent<Transform>();
	}
	
	// Update is called once per frame
	void Update () {
        var x = player_transform.position.x;
        var y = cam_transform.position.y;
        var z = player_transform.position.z;
        Vector3 new_cam_transform = new Vector3(x, y, z);
        cam_transform.position = new_cam_transform;
	}
}
