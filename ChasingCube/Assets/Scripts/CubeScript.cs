﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/// <summary>
///  Cube class the base of all type of cube in the game 
/// </summary>
public class CubeScript
{
    #region fields

    ///<summary>
    /// fields
    ///</summary>
    private float damagePt;
    private float health;  
    private float speed;
    GameConstants.CubeType cubeType;
    private GameObject gameObject;

    #endregion 

    #region constructor

    ///<summary>
    /// constructor 
    ///</summary>
    public CubeScript(GameConstants.CubeType cubeType, GameObject gameObject)
    {
        this.damagePt = GameConstants.DAMAGE_POINT;
        this.health = GameConstants.HEALTH;
        this.speed = GameConstants.INITIAL_SPEED;
        this.cubeType = cubeType;
        this.gameObject = gameObject;
    }

    #endregion

    #region properties

    ///<summary>
    /// Get and Set the damagePt of the cube
    ///</summary>
    public float DamagePt
    {
        get { return damagePt; } 
        set { damagePt = value; }
    }

    ///<summary>
    /// Get the health of the cube
    ///</summary>
    public float Health
    {
        get {return health;}
    }

    ///<summary>
    /// Get the speed of the cube
    ///</summary>
    public float Speed
    {
        get {return speed;}
        set {speed = value;}
    }

    ///<summary>
    /// Get the cube type
    ///</summary>
    public GameConstants.CubeType CubeType
    {
        get {return cubeType;}
    }

    ///<summary>
    /// Get the game object 
    ///</summary>
    public GameObject GameObject 
    {
        get {return gameObject;}
    }

    #endregion

    #region Public methods

    ///<summary>
    /// move the cube in the direction that we want
    ///</summary>
    public virtual void Move() 
    {
        if (gameObject.tag == GameConstants.HUNTED) 
        {
            float translationX = Input.GetAxis("Horizontal") * speed * Time.deltaTime * -1;
            float translationY = Input.GetAxis("Vertical") * speed * Time.deltaTime * -1;
            gameObject.transform.Translate(translationX,0,translationY);  
        } else if (gameObject.tag == GameConstants.HUNTER)
        {
            // we find the hunted cube to chase after it 
            GameObject hunted = GameObject.FindGameObjectWithTag(GameConstants.HUNTED);
            this.ChaseCube(hunted);
        } 
         

    }
    #endregion


    #region  private methods

    ///<summary>
    /// the hunter chase the hunted
    ///<param name="cube">cube we are chasing after</param>
    ///</summary>
    private void ChaseCube(GameObject cube) 
    {
        float distance = Vector3.Distance(gameObject.transform.position,  cube.gameObject.transform.position);
        if (distance > GameConstants.MINDIST)
        {
            gameObject.transform.position = Vector3.MoveTowards(gameObject.transform.position, 
                                cube.gameObject.transform.position, 2.0f * Time.deltaTime);
        }
        // stop the game object when it reach the hunted
        gameObject.GetComponent<Rigidbody>().velocity = Vector3.zero;
    }

    #endregion
 
}
