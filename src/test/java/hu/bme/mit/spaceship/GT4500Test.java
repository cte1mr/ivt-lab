package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.verification.VerificationMode;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTS1;
  private TorpedoStore mockTS2;

  @Before
  public void init(){
    mockTS1=mock(TorpedoStore.class);
    mockTS2=mock(TorpedoStore.class);
    this.ship = new GT4500(mockTS1, mockTS2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
      when(mockTS1.getTorpedoCount()).thenReturn(10);
      when(mockTS1.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1,times(1)).isEmpty();
    verify(mockTS1,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    //arrange
    when(mockTS1.getTorpedoCount()).thenReturn(10);
    when(mockTS1.isEmpty()).thenReturn(false);

    when(mockTS2.getTorpedoCount()).thenReturn(10);
    when(mockTS2.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1,times(1)).isEmpty();
    verify(mockTS1,times(1)).fire(1);
    verify(mockTS2,times(1)).isEmpty();
    verify(mockTS2,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_SecondaryEmpty(){
    //arrange
    when(mockTS2.isEmpty()).thenReturn(true);
    when(mockTS1.isEmpty()).thenReturn(false);

    // Act
   ship.fireTorpedo(FiringMode.SINGLE);
   ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1,times(2)).isEmpty();
    verify(mockTS2,times(1)).isEmpty();
    verify(mockTS1,times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondaryEmpty(){
    //arrange
    when(mockTS2.isEmpty()).thenReturn(true);
    when(mockTS1.isEmpty()).thenReturn(false);

    // Act
   ship.fireTorpedo(FiringMode.ALL);
   ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1,times(4)).isEmpty();
    verify(mockTS2,times(2)).isEmpty();
    verify(mockTS1,times(4)).fire(1);
  }
  @Test
  public void fireTorpedo_All_PrimaryEmpty(){
    //arrange
    when(mockTS2.isEmpty()).thenReturn(false);
    when(mockTS1.isEmpty()).thenReturn(true);

    // Act
   ship.fireTorpedo(FiringMode.ALL);
   ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTS1,times(2)).isEmpty();
    verify(mockTS2,times(4)).isEmpty();
    verify(mockTS2,times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_FirstEmpty(){
    //arrange
    when(mockTS1.isEmpty()).thenReturn(true);

    when(mockTS2.getTorpedoCount()).thenReturn(10);
    when(mockTS2.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1,times(1)).isEmpty();
    verify(mockTS1,times(0)).fire(1);
    verify(mockTS2,times(1)).isEmpty();
    verify(mockTS2,times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Alternating(){
    //arrange
    when(mockTS1.getTorpedoCount()).thenReturn(10);
    when(mockTS1.isEmpty()).thenReturn(false);

    when(mockTS2.getTorpedoCount()).thenReturn(10);
    when(mockTS2.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    //when(mockTS1.getTorpedoCount()).thenReturn(10); // fixme

    verify(mockTS1,times(1)).isEmpty();
    verify(mockTS1,times(1)).fire(1);
    verify(mockTS2,times(1)).isEmpty();
    verify(mockTS2,times(1)).fire(1);
  }
/*

  @Test
  public void fireTorpedo_Error(){
    //arrange
    when(mockTS1.getTorpedoCount()).thenReturn(0);
    when(mockTS1.isEmpty()).thenReturn(true);

    doThrow(new IllegalArgumentException()).when(mockTS1).fire(anyInt());

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTS1.fire(anyInt()));
    assertEquals(result,false);
  }
*/

}
