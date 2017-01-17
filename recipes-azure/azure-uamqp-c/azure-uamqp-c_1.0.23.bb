DESCRIPTION = "uAMQP is a general purpose C library for AMQP"
HOMEPAGE = "https://github.com/Azure/azure-uamqp-c"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4283671594edec4c13aeb073c219237a"

DEPENDS = "azure-c-shared-utility"

inherit cmake

SRC_URI = "\
	gitsm://github.com/Azure/azure-uamqp-c.git \
"
SRCREV = "6e6690fe9362206f91bbd33eb4cbdbc7a5689ce3"

PR = "r0"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS:BOOL=ON -Dskip_samples:BOOL=ON -Duse_installed_dependencies:BOOL=ON"

sysroot_stage_all_append () {
	sysroot_stage_dir ${D}${exec_prefix}/cmake ${SYSROOT_DESTDIR}${exec_prefix}/cmake

	# Fix CMake configs
	sed -i 's#${libdir}/libuamqp.so#${STAGING_LIBDIR}/libuamqp.so#g' ${SYSROOT_DESTDIR}${exec_prefix}/cmake/uamqp*
	sed -i 's#${includedir}/azureiot#${STAGING_INCDIR}/azureiot#g' ${SYSROOT_DESTDIR}${exec_prefix}/cmake/uamqp*
}

FILES_${PN} = "${libdir}/*.so"
FILES_${PN}-dev += "\
	${includedir} \
	${exec_prefix}/cmake \
"
FILES_${PN}-dbg += "${libdir}/.debug"
