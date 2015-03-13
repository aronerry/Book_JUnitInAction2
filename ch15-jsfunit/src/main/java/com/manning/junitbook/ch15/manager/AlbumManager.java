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
package com.manning.junitbook.ch15.manager;

import java.util.ArrayList;
import java.util.List;

import com.manning.junitbook.ch15.model.Album;

/**
 * A manager that we use to manipulate the beans. Normally there would be a database to connect to, but for the sake of
 * simplicity we use hardcoded fixed data.
 * 
 * @version $Id: AlbumManager.java 529 2009-08-16 18:59:05Z paranoid12 $
 */
public class AlbumManager
{
    final static List<Album> albums = new ArrayList<Album>();
    static
    {
        albums.add( new Album( "Achtung Baby", "U2", 9.97, 1991,"Pop", "http://upload.wikimedia.org/wikipedia/en/thumb/7/72/Achtung_Baby.png/100px-Achtung_Baby.png" ) );
        albums.add( new Album( "Master of Puppets", "Metallica", 12.97, 1986, "Metal", "http://upload.wikimedia.org/wikipedia/en/thumb/e/e0/Metallica_-_Master_of_Puppets.jpg/100px-Metallica_-_Master_of_Puppets.jpg" ) );
        albums.add( new Album( "Go: The Very Best of Moby", "Moby", 14.99, 2006, "Dance & Electronic", "http://upload.wikimedia.org/wikipedia/en/thumb/9/94/Go_very_best_of_moby.jpg/100px-Go_very_best_of_moby.jpg" ) );
        albums.add( new Album( "Ne sym angel", "Preslava", 4.99, 2008, "Chalga", "http://www.preslavaonline.com/Pictures/prd-1499.gif" ) );
        albums.add( new Album( "Me Against the World", "2Pac", 18.97, 1995, "Rap", "http://upload.wikimedia.org/wikipedia/en/thumb/3/3d/Meagainsttheworldcover.jpg/100px-Meagainsttheworldcover.jpg" ) );
    }

    /**
     * Get all available albums in the store.
     *
     * @return
     */
    public static List<Album> getAvailableAlbums()
    {
        return albums;
    }

    /**
     * Find an album by given name. Normally we would invoke a [select] query on the
     * DB or some search engine. For the sake of simplicity we just iterate over the 
     * hard-coded data.
     * 
     * @param title
     * @return
     */
    public static Album getAlbumByTitle( String title )
    {
        for ( Album album : albums )
        {
            if ( album.getName().equals( title ) )
            {
                return album;
            }
        }

        return null;
    }
}
