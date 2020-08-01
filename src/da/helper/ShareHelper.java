/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package da.helper;

import da.model.User;

/**
 *
 * @author BNC
 */
public class ShareHelper {
    public static User User = null;

    public static void logoff() {
        ShareHelper.User = null;
    }

    public static boolean checklog() {
        return ShareHelper.User != null;
    }
}
