// Copyright: 2018 https://www.megl.io
// License: http://www.megl.io/licenses/LICENSE-2.0

import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import de.heikoseeberger.sbtheader.License
import sbt.Def

object Licensing {

  private[this] val LicenseYear = "2022-2023"

  private[this] val licenseText =
    """Alberto Paro"""

  val settings = Seq(
    headerLicense := Some(HeaderLicense.ALv2(LicenseYear, licenseText))
  )

}
