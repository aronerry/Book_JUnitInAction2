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
package com.manning.junitbook.ch15.model;

/**
 * A model class that represents an Album.
 * 
 * @version $Id: Album.java 529 2009-08-16 18:59:05Z paranoid12 $
 */
public class Album
{
    private String name = null;
    
    private String author = null;
    
    private double price = 0;
    
    private int year = 0;
    
    private String style = null;
    
    private String imageURL = null;
    
    public String getStyle()
    {
        return style;
    }

    public void setStyle( String style )
    {
        this.style = style;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL( String imageURL )
    {
        this.imageURL = imageURL;
    }
    
    public Album(String name, String author, double price, int year, String style, String imageURL)
    {
        this.name = name;
        this.author = author;
        this.price = price;
        this.year = year;
        this.style = style;
        this.imageURL = imageURL;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor( String author )
    {
        this.author = author;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice( double price )
    {
        this.price = price;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear( int year )
    {
        this.year = year;
    }
}
