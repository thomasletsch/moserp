package org.moserp.inventory;

import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moserp.MainActivity_;
import org.moserp.common.test.AndroidDevicePreparingTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @ClassRule
    public static AndroidDevicePreparingTestRule devicePreparingTestRule = new AndroidDevicePreparingTestRule();

    @Rule
    public IntentsTestRule intentsTestRule = new IntentsTestRule<>(MainActivity_.class);

    @Test
    public void testScanInitiated() {
        onView(withId(R.id.action_scan)).perform(click());
        intending(hasAction("com.google.zxing.client.android.SCAN")).respondWith(new Instrumentation.ActivityResult(0, null));

    }

}