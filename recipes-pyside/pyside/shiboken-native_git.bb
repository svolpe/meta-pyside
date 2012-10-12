DESCRIPTION = "Shiboken is a plugin (front-end) for Generator Runner and a runtime library. It generates \
bindings for C++ libraries using CPython source code."

HOMEPAGE = "http://www.pyside.org"

DEPENDS = "apiextractor-native generatorrunner-native python-native"
PR = "r0"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=34337af480a8c452bfafe22a78fa20cb"

SRC_URI = "git://gitorious.org/pyside/shiboken.git;protocol=git;tag=eb293c2839cfbc726f0e085e1435c94b6f6561f9 \
           file://generator-rename-shiboken-dir.patch \
"

S = "${WORKDIR}/git"

SRC_URI[md5sum] = "946e8988e5f4c4bd62e774407fa80fee"
SRC_URI[sha256sum] = "82c6c24dc55458ed047eba9fe700894a3347cd53462b21a97b7b5f9180b2a896"
#OECMAKE_AR = "`echo ${AR} | sed 's/^\([^ ]*\).*/\1/'`"
#OE_CMAKE_AR = "${STAGING_BINDIR_TOOLCHAIN}/${TUNE_PKGARCH}${TARGET_VENDOR}-${TARGET_OS}-ar"
OE_CMAKE_AR = "/usr/bin/ar"
EXTRA_OECMAKE += " -DPYTHON_EXECUTABLE=${STAGING_BINDIR_NATIVE}/python-native/python2.7 \
						 -DCMAKE_AR=${OE_CMAKE_AR} \
"
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
}

#do_configure_append (){
#	ln -sf 
#}
inherit cmake native

