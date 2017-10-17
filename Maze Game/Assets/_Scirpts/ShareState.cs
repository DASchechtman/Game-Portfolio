using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ShareState : MonoBehaviour {
    Dictionary<string, int> gameData = new Dictionary<string, int>();

    public void add(string key, int value)
    {
        if (!gameData.ContainsKey(key))
        {
            gameData.Add(key, value);
        }
    }

    public void set(string key, int value)
    {
        if (gameData.ContainsKey(key))
        {
            gameData[key] = value;
        }
    }

    public int get(string key)
    {
        int retval = -1;
        if (gameData.ContainsKey(key))
        {
            retval = gameData[key];
        }
        return retval;
    }

    
}
