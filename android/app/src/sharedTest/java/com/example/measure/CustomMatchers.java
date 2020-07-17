package com.example.measure;

import android.view.View;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

/**
 * Custom Hamcrest matchers for testing.
 */
public class CustomMatchers {
    /**
     * Create a matcher that matches if the examined object matches the
     * view in the given row of the recyclerview.
     *
     * @param recyclerViewMatcher matcher for the recyclerview
     * @param expectedPos         index of view in the recyclerview
     * @return matcher to match with view in given row inside of recyclerview
     */
    public static Matcher<View> rowAtPos(Matcher<View> recyclerViewMatcher,
                                         int expectedPos) {
        return new TypeSafeMatcher<View>() {
            /**
             * Check that the given item matches the view in the expected row
             * of the recyclerview.
             *
             * @param item view to compare
             * @return true if the item matches the view in the expected row of
             *         the recyclerview; false otherwise
             */
            @Override
            protected boolean matchesSafely(View item) {
                ViewParent itemParent = item.getParent();

                if (!(itemParent instanceof RecyclerView)) {
                    return false;
                }
                else if (!(recyclerViewMatcher.matches(itemParent))) {
                    // Cannot match if the expected recycler view is different
                    // from the actual item's parent.
                    return false;
                }

                RecyclerView itemRec = (RecyclerView) itemParent;
                RecyclerView.ViewHolder expectedViewholder =
                        itemRec.findViewHolderForAdapterPosition(expectedPos);

                return expectedViewholder != null && item == expectedViewholder.itemView;
            }

            /**
             * Describe failure message when a test fails.
             *
             * @param description failure message description
             */
            @Override
            public void describeTo(Description description) {
                description.appendText("recycler view row match of <"
                        + recyclerViewMatcher + "> at position "
                        + expectedPos);
            }
        };
    }

    /**
     * Create a matcher that matches if the given object is equal through
     * reflection.
     *
     * @param expectedObj expected object
     * @return matcher that uses reflection to check for equality
     */
    public static <T> Matcher<T> reflectEquals(T expectedObj) {
        return new TypeSafeMatcher<T>() {
            @Override
            protected boolean matchesSafely(T item) {
                return new ReflectionEquals(expectedObj).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("reflect equals of <" + expectedObj
                        + ">");
            }
        };
    }
}
