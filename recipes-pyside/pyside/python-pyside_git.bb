QTV = "4.7"
DESCRIPTION = "Python Bindings for Qt ${QTV}"
LICENSE = "LGPLv2.1 | GPLv3"
#LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=fbc093901857fcd118f065f900982c24 \
#                    file://LICENSE.GPL3;md5=babc5b6b77441da277f5c06b2e547720 \
#                    file://LGPL_EXCEPTION.txt;md5=411080a56ff917a5a1aa08c98acae354"
DEPENDS = "apiextractor-native generatorrunner-native shiboken-native"
RDEPENDS_${pn} = "python-core"
PROVIDES = "python-pyside"
INC_PR = "r2"

RDEPENDS_${PN} = " \
 python-lang \
"

SRC_URI = "git://gitorious.org/pyside/pyside.git;protocol=git;tag=6df4b307c5aec758ad954ab8717f5e85b44e2ae5 \
			  file://support-qws.patch \
	 			file://MacroPushRequiredVars.cmake \
				file://FindQt4.cmake \
"
# file://MacroPushRequiredVars.cmake \

# NOTE this should be reworked when a x11 version of qt4 is needed
inherit cmake	
EXTRA_OECMAKE = " \
	-DSITE_PACKAGE=${STAGING_LIBDIR}/python2.7/site-packages \
	-DPYTHON_INCLUDE_DIR:PATH=${STAGING_INCDIR}/python2.7 \
	-DPYTHON_LIBRARIES:PATH=${STAGING_LIBDIR}/python2.7 \
   -DQT_LIBRARY_DIR=${STAGING_INCDIR} \
   -DQT_HEADERS_DIR=${STAGING_INCDIR}/qtopia \
   -DQT_QTCORE_INCLUDE_DIR=${STAGING_INCDIR}/qtopia/QtCore \
"
#EXTRA_OECMAKE += " \
#	-DSITE_PACKAGE=${STAGING_LIBDIR}/python2.7/site-packages \
#	-DPYTHON_INCLUDE_DIR:PATH=${STAGING_INCDIR}/python2.7 \
#   -DQT_INCLUDE_DIR:PATH=${OE_QMAKE_INCDIR_QT} \
#   -DQT_LIBRARY_DIR=${STAGING_TARGET_LIBDIR} \
#   -DQT_LIBINFIX=${QT_LIBINFIX} \
#   -DQT_DIR_NAME=${QT_DIR_NAME} \
#   -DCMAKE_LIBRARY_PATH=${STAGING_TARGET_LIBDIR} \
#   -DCMAKE_TOOLCHAIN_SYSTEM_ROOT:PATH=${STAGING_DIR_TARGET} \
#   -DCMAKE_STAGING_DIR_TARGET:PATH=${STAGING_DIR_TARGET} \
#   -DCMAKE_STAGING_DIR_NATIVE:PATH=${STAGING_DIR_NATIVE} \
#   -DCMAKE_TOOLCHAIN_SYSTEM_ROOT:PATH=${STAGING_DIR_TARGET} \
#   -DCMAKE_STAGING_DIR_TARGET:PATH=${STAGING_DIR_TARGET} \
#   -DQT_INSTALL_LIBS=${OE_QMAKE_LIBDIR_QT} \
#   -DQT_INCLUDE_DIR=${OE_QMAKE_INCDIR_QT} \
#   -DQT_QTCORE_INCLUDE_DIR=${OE_QMAKE_INCDIR_QT}/QtCore \
#"

#do_generate_toolchain_file_append() {
#   # even search in shiboken cmake modules path for modules
#   echo "set( CMAKE_MODULE_PATH ${STAGING_LIBDIR}/cmake/Shiboken-${PV} \${CMAKE_MODULE_PATH} )" >> ${WORKDIR}/toolchain.cmake
#   echo "set( CMAKE_MODULE_PATH ${S}/cmake/Modules \${CMAKE_MODULE_PATH} )" >> ${WORKDIR}/toolchain.cmake
#   echo "set( PYTHON_INCLUDE_DIR ${STAGING_INCDIR}/python2.7 )" >> ${WORKDIR}/toolchain.cmake
#}

#do_configure_prepend() {
#   mkdir -p ${S}/cmake/Modules
#   cp ${WORKDIR}/FindQt4.cmake ${S}/cmake/Modules/FindQt4.cmake
#   cp ${WORKDIR}/MacroPushRequiredVars.cmake ${S}/cmake/Modules/MacroPushRequiredVars.cmake
#}

#do_configure_prepend() {
#   mkdir -p ${S}/cmake/Modules
#   cp ${WORKDIR}/MacroPushRequiredVars.cmake ${S}/cmake/Modules/MacroPushRequiredVars.cmake
#}

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=15a1ca44f90f3ab457d6a4fe7c0f3a19"

S = "${WORKDIR}/git"

SRC_URI[md5sum] = "946e8988e5f4c4bd62e774407fa80fee"
SRC_URI[sha256sum] = "82c6c24dc55458ed047eba9fe700894a3347cd53462b21a97b7b5f9180b2a896"


