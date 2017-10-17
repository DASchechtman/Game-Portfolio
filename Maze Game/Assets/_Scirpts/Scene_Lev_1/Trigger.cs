using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Trigger : MonoBehaviour {
    private Text winText;
    private int show = 0;
    GameObject state;

	// Use this for initialization
	void Start () {
        GameObject state = GameObject.Find("Win Text");
        if(state != null)
        {
            winText = state.GetComponent<Text>();
            winText.text = "";
        }
	}
	
	// Update is called once per frame
	void Update () {
        if(show == 1)
        {
            winText.text = "You Win";
            show = 0;
        }

		
	}

    private void OnTriggerEnter(Collider other)
    {
        show = 1;
        this.GetComponent<SpriteRenderer>().enabled = false;
    }
}
