using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Load : MonoBehaviour {

    public int scene_index;

	// Use this for initialization
	void Start () {
        Button btn = GetComponent<Button>();
        btn.onClick.AddListener(load);
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    private void load()
    {
        SceneManager.LoadScene(scene_index);
    }

}
