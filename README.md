
# SnapshotDemo

A way to reproduce a letter spacing rendering discrepancy between platform and Compose, powered by [Paparazzi](https://github.com/cashapp/paparazzi).

Here is an example of how it manifests itself:

![delta-com jeppeman snapshotdemo_DemoTest_testXmlDefinedTextSize 21](https://user-images.githubusercontent.com/5256210/195853650-249d99f3-de3d-4fef-b256-52f1912fccb0.png)

Never mind the red and yellow backgrounds, they are just there to force failures from Paparazzi so that we can see the diffs more easily.

The layout of the left snapshot (platform) is defined as follows: 

![Screenshot 2022-10-14 at 15 07 45](https://user-images.githubusercontent.com/5256210/195854729-80a24ddf-3af8-4484-bf4e-3cd19cfa6a7c.png)

And the right one (Compose), like so:

![Screenshot 2022-10-14 at 15 08 09](https://user-images.githubusercontent.com/5256210/195854830-e5cf5bdc-a555-42ac-a902-813b8fee85c0.png)

The interesting part about this is that it's only reproducible for certain text sizes(!) (e.g. 21sp, 25sp, 26sp, 27sp..), and **only** when these text sizes are set in XML(!), when programmatically setting the text size it is not reproducible any more.

### Reproducing the discrepancy

First record the golden value snapshots for the platform `TextView`s
```shell
./gradlew :test:recordPaparazziRecord
```
This will create the golden values with text sizes from 20..30sp for the platform `TextView` (find them in `test/src/test/snapshots/images`).

Then run the verification against the Compose counterparts:
```shell
./gradlew :test:verifyPaparazziVerification
```
The diffs will be stored in `test/out/failures`. Now, open `test/out/failures/delta-com.jeppeman.snapshotdemo_DemoTest_testXmlDefinedTextSize[21].png` (text size is set through XML) and see the letter spacing discrepancy; however, it is not present in `test/out/failures/delta-com.jeppeman.snapshotdemo_DemoTest_testProgrammaticallyDefinedTextSize[21].png` (text size set programmatically), nor in `test/out/failures/delta-com.jeppeman.snapshotdemo_DemoTest_testXmlDefinedTextSize[20].png` for that matter.

### Conclusion
So, as mentioned above: only for certain text sizes and only when said text sizes are declared through XML. We can most likely conclude that this is not a problem with Compose, but rather with how letter spacing is initialized in `TextView` when reading attributes from XML.
It might never manifest itself at runtime either I suspect, it might very well be that it fixes itself on the next layout cycle.
