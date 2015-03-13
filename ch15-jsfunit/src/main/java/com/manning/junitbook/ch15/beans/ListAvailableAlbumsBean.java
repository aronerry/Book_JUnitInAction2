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

import java.util.ArrayList;
import java.util.List;

import com.manning.junitbook.ch15.manager.AlbumManager;
import com.manning.junitbook.ch15.model.Album;

/**
 * A bean that we use to display all the albums that are currently available in the shop. Notice that we display only a
 * fixed amount of albums. Normally you would get the list of albums from a DB but we don't want to involve a DB in this
 * example as we want to keep it as simple as possible.
 * 
 * @version $Id: ListAvailableAlbumsBean.java 529 2009-08-16 18:59:05Z paranoid12 $
 */
public class ListAvailableAlbumsBean
{

    private List<Album> albums = new ArrayList<Album>();

    public List<Album> getAlbums()
    {
        this.albums = AlbumManager.getAvailableAlbums();

        return albums;
    }

    public void setAlbums( List<Album> albums )
    {
        this.albums = albums;
    }
}