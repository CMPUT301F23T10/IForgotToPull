package com.example.boeing301house;

        import static androidx.test.espresso.Espresso.onView;
        import static androidx.test.espresso.action.ViewActions.click;
        import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
        import static androidx.test.espresso.action.ViewActions.typeText;
        import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
        import static androidx.test.espresso.assertion.ViewAssertions.matches;
        import static androidx.test.espresso.matcher.ViewMatchers.hasBackground;
        import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
        import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
        import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
        import static androidx.test.espresso.matcher.ViewMatchers.withId;
        import static androidx.test.espresso.matcher.ViewMatchers.withText;

        import static junit.framework.TestCase.assertFalse;
        import static junit.framework.TestCase.assertTrue;
        import static org.hamcrest.CoreMatchers.is;

        import android.content.Context;
        import android.content.SharedPreferences;

        import androidx.test.espresso.action.ViewActions;
//import androidx.test.espresso.contrib.PickerActions;

        import androidx.test.ext.junit.rules.ActivityScenarioRule;
        import androidx.test.ext.junit.runners.AndroidJUnit4;
        import androidx.test.filters.LargeTest;
        import androidx.test.platform.app.InstrumentationRegistry;

        import com.example.boeing301house.itemlist.ItemListActivity;

        import org.hamcrest.Matchers;
        import org.junit.BeforeClass;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileTests {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @BeforeClass
    public static void setup(){
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences pref = targetContext.getSharedPreferences("mypref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("firststart", true);
        editor.commit(); // apply changes
    }
    /**
     * Test UUID is correctly reflected in profile on launch
     */

    @Test
    public void testProfileChange() {
        onView(withId(R.id.sign_in_button)).perform(click());
        onView(withId(R.id.itemListProfileButton)).perform(click());
        // Update username
        onView(withId(R.id.editUserNameButton)).perform(click());
        onView(withId(R.id.userNameDialogInputLayout)).perform(click());
        onView(withId(R.id.userNameDialogEditText)).perform(ViewActions.typeText("NEW USERNAME"), ViewActions.closeSoftKeyboard());
        onView(withText("Confirm")).perform(click());
        // Check username update
        onView(withId(R.id.userNameTextView)).check(matches((withText("NEW USERNAME"))));
        // Leave activity and come back to confirm username
        onView(withId(R.id.closeViewButton)).perform(click());
        onView(withId(R.id.itemListProfileButton)).perform(click());
        onView(withId(R.id.userNameTextView)).check(matches((withText("NEW USERNAME"))));
    }

    @Test
    public void testDarkModeToggle() {
        onView(withId(R.id.sign_in_button)).perform(click());
        onView(withId(R.id.itemListProfileButton)).perform(click());
        // Update username
        onView(withId(R.id.toggleThemeButton)).perform(click());
        // Check dark mode has been updated in preferences
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences pref = targetContext.getSharedPreferences("mypref",Context.MODE_PRIVATE);
        assertFalse(pref.getBoolean("light",true));
        // check light mode icon is now displayed
        onView(withId(R.id.toggleThemeButton)).check(matches(withDrawable(R.drawable.ic_light_mode_24dp))));
        onView(withId(R.drawable.ic_light_mode_24dp)).check(matches(isDisplayed()));
        // Leave activity and come back to confirm correct icon is displayed
        onView(withId(R.id.closeViewButton)).perform(click());
        onView(withId(R.id.itemListProfileButton)).perform(click());
        onView(withId(R.drawable.ic_light_mode_24dp)).check(matches(isDisplayed()));
    }
}

