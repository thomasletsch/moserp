package org.moserp.inventory;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moserp.MainActivity_;
import org.moserp.common.test.AndroidDevicePreparingTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ProductSearchTest {

    @ClassRule
    public static AndroidDevicePreparingTestRule devicePreparingTestRule = new AndroidDevicePreparingTestRule();

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity_.class);

    @Test
    public void testSearchForProduct() throws InterruptedException {
        onView(withId(R.id.action_search)).perform(click());
        onView(withHint(R.string.search_hint)).perform(typeText("m\n"));
        SystemClock.sleep(500);
        checkProductDetailsActivity();
    }

    private void checkProductDetailsActivity() {
        onView(withId(R.id.titleProductDetails)).check(matches(isDisplayed()));
        onView(withId(R.id.productId)).check(matches(withText("1")));
        onView(withId(R.id.facilityName)).check(matches(withText("Zentrale")));

    }

}
