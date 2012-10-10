DESCRIPTION = "Shiboken is a plugin (front-end) for Generator Runner and a runtime library. It generates \
bindings for C++ libraries using CPython source code."

HOMEPAGE = "http://www.pyside.org"

DEPENDS = "apiextractor-native generatorrunner-native python-native"
PR = "r0"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=34337af480a8c452bfafe22a78fa20cb"

SRC_URI = "git://gitorious.org/pyside/shiboken.git;protocol=git;tag=eb293c2839cfbc726f0e085e1435c94b6f6561f9 \
			  file://MacroPushRequiredVars.cmake \
			  file://FindQt4.cmake \
			  file://rename-shiboken-pkg.patch \
			  "

S = "${WORKDIR}/git"
inherit cmake pkgconfig python-dir

SRC_URI[md5sum] = "946e8988e5f4c4bd62e774407fa80fee"
SRC_URI[sha256sum] = "82c6c24dc55458ed047eba9fe700894a3347cd53462b21a97b7b5f9180b2a896"
#OECMAKE_AR = "`echo ${AR} | sed 's/^\([^ ]*\).*/\1/'`"
QT_LIBINFIX = "E"
QT_DIR_NAME = "qtopia"

OE_CMAKE_AR = "${STAGING_BINDIR_TOOLCHAIN}/${AR}"
EXTRA_OECMAKE += " -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python-native/python2.7 \
						 -DCMAKE_AR=${OE_CMAKE_AR} \
   					 -DQT_LIBINFIX=${QT_LIBINFIX} \
   					 -DQT_DIR_NAME=${QT_DIR_NAME} \
						 -DQT_INCLUDE_DIR=${STAGING_INCDIR}/qtopia \
						 -DQT_QTCORE_INCLUDE_DIR=${STAGING_INCDIR}/qtopia/QtCore \
						 -DQT_LIBRARY_DIR=${STAGING_LIBDIR} \
   					 -DSITE_PACKAGE=${STAGING_LIBDIR}/python2.7/site-packages \
   					 -DPYTHON_INCLUDE_DIR:PATH=${STAGING_INCDIR}/python2.7 \
   					 -DPYTHON_LIBRARIES:PATH=${STAGING_LIBDIR}/python2.7 \
                   -DQT_LIBRARY_DIR=${STAGING_INCDIR} \
                   -DQT_HEADERS_DIR=${STAGING_INCDIR}/qtopia \
                   -DLIBXSLT_INCLUDE_DIR=${STAGING_INCDIR}/libxslt \
                   -DLIBXSLT_LIBRARIES=${STAGING_LIBDIR}/libxslt.so \
						 -DCMAKE_STAGING_DIR_NATIVE:PATH=${STAGING_DIR_NATIVE} \
"

#  LIBXSLT_INCLUDE_DIR - the LibXslt include directory
#  LIBXSLT_LIBRARIES - Link these to LibXslt

#                   -DQT_QTTEST_LIBRARY=${STAGING_LIBDIR}/libQtTestE.so \
#                   -DQT_QTTEST_LIBRARY_RELEASE=${STAGING_LIBDIR}/libQtTestE.so \
#                   -DQT_QTCORE_INCLUDE_DIR=${STAGING_INCDIR}/qtopia/QtCore \
#						 -DQT_QTCORE_LIBRARY:STRING=${STAGING_LIBDIR}/libQtCoreE.so \
#						 -DQT_QTGUI_LIBRARY:STRING=${STAGING_LIBDIR}/libQtGuiE.so \
#						 -DQT_QTNETWORK_LIBRARY:STRING=${STAGING_LIBDIR}/libQtNetworkE.so \
#						 -DQT_QTWEBKIT_LIBRARY:STRING=${STAGING_LIBDIR}/libQtWebkitE.so \
#						 -DQT_QTSVG_LIBRARY:STRING=${STAGING_LIBDIR}/libQtSvgE.so \
#						 -DQT_QTXML_LIBRARY:STRING=${STAGING_LIBDIR}/libQtXmlE.so \
#						 -DQT_QTSQL_LIBRARY:STRING=${STAGING_LIBDIR}/libQtSqlE.so \
#"
#HAS_QT_MODULE(QT_QTCORE_FOUND QtCore)
#HAS_QT_MODULE(QT_QTGUI_FOUND QtGui)
#HAS_QT_MODULE(QT_QTNETWORK_FOUND QtNetwork)
#HAS_QT_MODULE(QT_QTWEBKIT_FOUND QtWebKit)
#HAS_QT_MODULE(QT_QTSVG_FOUND QtSvg)
#HAS_QT_MODULE(QT_QTXML_FOUND QtXml)
#HAS_QT_MODULE(QT_QTTEST_FOUND QtTest)
#HAS_QT_MODULE(QT_QTOPENGL_FOUND QtOpenGL)
#HAS_QT_MODULE(QT_QTSQL_FOUND QtSql)


#						-DPYTHON_INCLUDE_DIR=${STAGING_INCDIR_NATIVE}/python2.7 \
#						-DPYTHON_LIBRARY=${STAGING_LIBDIR_NATIVE}/python2.7/site-packages \
#"
# The following exports are needed to let the cmake build configuration succeed without
# errors when detecting the correct python version
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR

addtask do_fix_generator_names after do_patch before do_configure

do_fix_generator_names() {
    mv ${S}/generator/shiboken ${S}/generator/shiboken-src
    ln -s ${STAGING_BINDIR_NATIVE}/shiboken ${S}/generator/shiboken
}

FILES_${PN}-gdb += "${libdir}/python2.7/site-packages/.debug/shiboken*"
FILES_${PN}-dev += "${libdir}/cmake/ ${libdir}/pkgconfig"
FILES_${PN} += "${libdir}/python2.7/site-packages/shiboken*"

#do_configure_prepend() {
#   cp ${WORKDIR}/MacroPushRequiredVars.cmake ${S}/cmake/Modules/MacroPushRequiredVars.cmake
#   cp ${WORKDIR}/FindQt4.cmake ${S}/cmake/Modules/FindQt4.cmake
#}

#STAGING_LIBDIR_NATIVE = "${STAGING_DIR}/${BUILD_SYS}${prefix}/lib"
#STAGING_INCDIR_NATIVE = "${STAGING_DIR}/${BUILD_SYS}${prefix}/include"

# NOTE: This needs to be appended to do_configure as pkgconfig.bbclass uses
# do_install_prepend for it's fixups and we need to run before it!

#do_configure_append() {
#   # Fixup generated *.cmake and *.pc files for wrong paths
#   for i in `find ${S}/data -name "*.cmake" -type f` ; do \
#	sed -i -e 's:${STAGING_LIBDIR_NATIVE}:${STAGING_LIBDIR}:g' CMakeCache.txt 
#         -e 's:${STAGING_LIBDIR_NATIVE}:${STAGING_LIBDIR}:g' \
#         $i
#   done
#}

#   # We need do this here a second time (pkgconfig.bbclass already replaces the -L.. and
#   # -I .. ones) as there are additional variables for python in the pkgconfig file
#   for i in `find ${S}/data -name "*.pc" -type f` ; do \
#      sed -i -e 's:${STAGING_BINDIR_NATIVE}:${bindir}:g' \
#         -e 's:${STAGING_INCDIR}:${includedir}:g' \
#         -e 's:${STAGING_LIBDIR}:${libdir}:g' \
#         -e 's:${STAGING_INCDIR_NATIVE}:${includedir}:g' \
#         -e 's:${STAGING_LIBDIR_NATIVE}:${libdir}:g' \
#         -e 's:-lshiboken:-lshiboken-${PYTHON_DIR}:g' \
#         $i
#   done

#do_configure_append (){
#	ln -sf 
#}
#inherit cmake 

