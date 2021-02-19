package neil;

import randomreverser.ReverserDevice;
import randomreverser.call.FilteredSkip;
import randomreverser.call.NextInt;

public class Test2 {
    public static void main(String[] args) {
        ReverserDevice device = new ReverserDevice();
        device.setVerbose(true);
        for (int i = 1; i <=3; ++i) {
            for (int j = 1; j <= 6; ++j) {
                device.addCall(FilteredSkip.filter(r -> r.nextInt(3) != 0));
                device.addCall(NextInt.withValue(4,0));
            }
        }
        device.streamSeeds().forEach(System.out::println);
    }
}
