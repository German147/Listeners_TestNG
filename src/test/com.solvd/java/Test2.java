import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(MyListener.class)
public class Test2 {
    @Test
    public void successTestTwo(){
        System.out.println("Im a test in run 3");
        Assert.assertEquals(3,3);
    }
}
