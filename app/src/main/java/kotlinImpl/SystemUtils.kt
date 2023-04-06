package kotlinImpl

import android.content.Context
import android.text.TextUtils

class SystemUtils {

    companion object {
        /**
         * 检查对应报名是否安装
         * 1.android 11 以上获取非声明的第三方应用包名会获取不到
         * 2.android 13 以上新增获取用户安装列表权限 默认不开腔
         * @param pkg 报名
         * @param context context
         */
        fun packageInstall(pkg: String?, context: Context?): Boolean{
            if (TextUtils.isEmpty(pkg) || context == null) {
                return false
            }
            val packageInfo = try {
                context.packageManager.getPackageInfo(pkg!!, 0)
            } catch (e: Exception) {
                //PackageManager.NameNotFoundException
                null
            }
            return packageInfo != null && TextUtils.equals(packageInfo.packageName, pkg)
        }
    }
}