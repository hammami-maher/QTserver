#
# This file was derived from the 'ACTIA-QTserver' example recipe in the
# Yocto Project Development Manual.
#

PR = "r1"
SUMMARY = "ACTIA QTserver"
DESCRIPTION = "ACTIA QTserver application for Edison which demonstrates ACTIA QTserver capabilities through the integration of an add-on breadboard that hosts temperature and LED resources"
HOMEPAGE = "https://www.iotivity.org/"
DEPENDS = "iotivity mraa"
SECTION = "QTserver"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://ACTIA-QTserver.tar.gz \
          "
#           file://0001-Build-Use-LDFLAGS-variable-from-env-and-add-pthread-.patch \
#           file://0003-server-Port-to-iotivity-1.2.0.patch \
#           file://0004-build-Use-pkg-config.patch \
#           "

inherit pkgconfig

TARGET_CC_ARCH += "${LDFLAGS}"

S = "${WORKDIR}/ACTIA-QTserver"

IOTIVITY_BIN_DIR = "/opt/iotivity"
IOTIVITY_BIN_DIR_D = "${D}${IOTIVITY_BIN_DIR}"

do_compile_prepend() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"
    export LD_FLAGS="${LD_FLAGS}"
}

do_install() {
    install -d ${IOTIVITY_BIN_DIR_D}/apps/ACTIA-QTserver
    install -c -m 555 ${S}/ACTIA-QTserver ${IOTIVITY_BIN_DIR_D}/apps/ACTIA-QTserver
}

FILES_${PN} = "${IOTIVITY_BIN_DIR}/apps/ACTIA-QTserver/ACTIA-QTserver" 
FILES_${PN}-dbg = "${IOTIVITY_BIN_DIR}/apps/ACTIA-QTserver/.debug"
RDEPENDS_${PN} += "iotivity-resource mraa"
BBCLASSEXTEND = "native nativesdk"
PACKAGE_DEBUG_SPLIT_STYLE = "debug-without-src"
