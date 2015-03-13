/* 
 * ========================================================================
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ========================================================================
 */
package com.manning.junitbook.ch15.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.manning.junitbook.ch15.manager.AlbumManager;
import com.manning.junitbook.ch15.model.Album;

/**
 * A bean that we use to list the details for a given bean.
 * 
 * @version $Id: AlbumDetailsBean.java 529 2009-08-16 18:59:05Z paranoid12 $
 */
public class AlbumDetailsBean
{

    private String status = null;

    private HttpServletRequest request = null;

    /**
     * The album instance we want to show details for.
     */
    private Album album = null;

    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum( Album album )
    {
        this.album = album;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    /**
     * The method that sets the desired album.
     * 
     * @param album
     * @return
     */
    public String showAlbumDetails()
    {
        HttpServletRequest request = getRequest();

        String name = request.getParameter( "albumName" );

        if ( name == null )
        {
            return "";
        }

        setAlbum( AlbumManager.getAlbumByTitle( name ) );

        return "showAlbumDetails";
    }

    /**
     * Return the request.
     * 
     * @return
     */
    protected HttpServletRequest getRequest()
    {
        if ( this.request == null )
        {
            return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        }
        else
        {
            return this.request;
        }
    }

    /**
     * Sets the request. We use this method from our tests to set the mock request.
     * 
     * @param request
     */
    public void setRequest( HttpServletRequest request )
    {
        this.request = request;
    }

    /**
     * Go back to the listing page.
     * 
     * @return
     * @throws InterruptedException
     */
    public String cancel()
    {
        return "cancel";
    }

    /**
     * Purchase the given album.
     * 
     * @return
     * @throws InterruptedException
     */
    public void purchase()
        throws InterruptedException
    {
        Thread.sleep( 1500 );
        // empty implementation
        System.out.println( "Here we must implement the purchase logic." );
    }

}
