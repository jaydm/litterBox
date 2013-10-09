
package net.jnwd.litterBox.contentProvider;

import android.net.Uri;

public final class BoxContract {
    public static final String Authority = "net.jnwd.litterBox.contentProvider";
    public static final Uri Content_Uri = Uri.parse("content://" + Authority);
    
}
