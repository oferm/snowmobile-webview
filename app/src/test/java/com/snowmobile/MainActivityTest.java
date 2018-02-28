package com.snowmobile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by scr on 2/28/18.
 */
public class MainActivityTest {
    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = new MainActivity();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCssFile() throws Exception {
        Assert.assertEquals("css/start.css",
                mainActivity.getCssFile("http://snowheads.com/"));
        Assert.assertEquals("css/start.css",
                mainActivity.getCssFile("http://snowheads.com/mx/index.php"));

        Assert.assertEquals("css/forumlist.css",
                mainActivity.getCssFile("http://snowheads.com/ski-forum/"));
        Assert.assertEquals("css/forumlist.css",
                mainActivity.getCssFile("http://snowheads.com/ski-forum/index.php"));


        Assert.assertEquals("css/viewforum.css",
                mainActivity.getCssFile("http://snowheads.com/ski-forum/viewforum.php?f=2"));
        Assert.assertEquals("css/viewforum.css",
                mainActivity.getCssFile("http://snowheads.com/ski-forum/snow_reports.php"));


        Assert.assertEquals("css/viewtopic.css",
                mainActivity.getCssFile("http://snowheads.com/ski-forum/viewtopic.php?t=132931"));

    }

}