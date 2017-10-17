using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Set_Settings : MonoBehaviour {
    public int sensitivity;
    private ShareState set_sensitivity;

    private void Awake()
    {
        set_sensitivity = GameObject.Find("GameData").GetComponent<ShareState>();
    }

    // Use this for initialization
    void Start () {
        Button btn = GetComponent<Button>();
        btn.onClick.AddListener(click);
	}
	
	// Update is called once per frame
	void Update () {
		
	}
    
    void click()
    {
        set_sensitivity.set("x", sensitivity);
        set_sensitivity.set("y", sensitivity);
    }
}
