package com.fpt.minesweeper;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;

public class MinesweeperTest {
    Minesweeper minesweeper;
    String input;
    
    @Before 
    public void init() {
        minesweeper= new Minesweeper();
        
        input = "5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
    
    @Test
    public void testGridSize() {
        
        minesweeper.enterGridSize();
        assertEquals(input, "" + (minesweeper.gridSize));
    }
    
    @Test
    public void testCellVisibleCount() {
        
        minesweeper.enterGridSize();
        minesweeper.setupMineLocation();
        
        assertEquals(("" + Integer.parseInt(input)), "" +  minesweeper.getCellVisible().length);
    }
    
    @Test
    public void testCellHiddenCount() {
        
        minesweeper.enterGridSize();
        minesweeper.setupMineLocation();
        
        assertEquals(("" + Integer.parseInt(input)), "" +  minesweeper.getCellHidden().length);
    }
    
    @Test
    public void testHasCellHidden() {
        boolean hasCellHidden= false;
        minesweeper.enterGridSize();
        minesweeper.setupMineLocation();
        minesweeper.showVisible();
        
        System.out.println("minesweeper.getCellVisible().length: " + minesweeper.getCellHidden().length);
        
        int[][] cellHidden= minesweeper.getCellHidden();
        for (int i= 0; i < cellHidden.length; i++ ) {
            for (int j= 0; j < cellHidden.length; j++ ) {
                
                //System.out.println("cellVisibile[i][j]: " + cellHidden[i][j]);
                if (cellHidden[i][j] != 0 && cellHidden[i][j] != 50 && cellHidden[i][j] != -200) {
                    hasCellHidden= true;
                }
                
            }
        }
       
        assertTrue(hasCellHidden);
    }
    
    @Test
    public void testPlayerWin() {
        minesweeper.enterGridSize();
        minesweeper.setupMineLocation();
        
        int[][] cellVisible= minesweeper.getCellVisible();
        for (int i= 0; i < cellVisible.length; i++ ) {
            for (int j= 0; j < cellVisible.length; j++ ) {
                minesweeper.setCellVisible(i, j, 1);
                minesweeper.setCellHidden(i, j, -200);
            }
        }
       
        assertTrue(minesweeper.checkWin());
    }
    
    @Test
    public void testPlayerLoose() {
        minesweeper.enterGridSize();
        minesweeper.setupMineLocation();
        
        int[][] cellVisible= minesweeper.getCellVisible();
        for (int i= 0; i < cellVisible.length; i++ ) {
            for (int j= 0; j < cellVisible.length; j++ ) {
                minesweeper.setCellVisible(i, j, 0);
                minesweeper.setCellHidden(i, j, 0);
            }
        }
       
        assertTrue(!minesweeper.checkWin());
    }
}
