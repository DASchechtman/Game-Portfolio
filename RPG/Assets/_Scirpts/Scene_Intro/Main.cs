using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Main : MonoBehaviour {

    private ShareState s;

	// Use this for initialization
	void Start () {
        s = GetComponent<ShareState>();
        DontDestroyOnLoad(this);
        s.add("x", 1);
        s.add("y", 1);
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
