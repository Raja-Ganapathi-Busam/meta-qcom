SUMMARY = "Qualcomm IMSDK GStreamer Plugins (QTI OSS)"
DESCRIPTION = "Open-source Qualcomm IMSDK GStreamer multimedia, CV, ML, and messaging plugins"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=223037c4be0bfc6cf757035432adf983"

inherit cmake pkgconfig

SRC_URI = "git://github.com/qualcomm/gst-plugins-imsdk;branch=main;protocol=https"

SRCREV = "93610673ea88033c0b0d46ada93a55a24d9f3b75"
PV = "0.0+git"

DEPENDS += "\
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    qcom-fastcv-binaries \
    virtual/libgbm \
"
RDEPENDS:${PN} += "\
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    qcom-fastcv-binaries \
"

PACKAGECONFIG ??= "\
    batch metamux rtspbin smartvencbin socket tools videotemplate \
    vcomposer voverlay vsplit vtransform \
    metatransform mldemux mlmetaextractor mlmetaparser mltools mlvconverter \
"

PACKAGECONFIG:append:qcom = "\
    redissink restrictedzone \
    mlpostprocess \
    msgbroker \
"

# Software plugins -- software
PACKAGECONFIG[batch]                = "-DENABLE_GST_PLUGIN_BATCH=1,             -DENABLE_GST_PLUGIN_BATCH=0"
PACKAGECONFIG[metamux]              = "-DENABLE_GST_PLUGIN_METAMUX=1,           -DENABLE_GST_PLUGIN_METAMUX=0"
PACKAGECONFIG[redissink]            = "-DENABLE_GST_PLUGIN_REDISSINK=1,         -DENABLE_GST_PLUGIN_REDISSINK=0,                hiredis, hiredis"
PACKAGECONFIG[restrictedzone]       = "-DENABLE_GST_PLUGIN_RESTRICTED_ZONE=1,   -DENABLE_GST_PLUGIN_RESTRICTED_ZONE=0,          opencv, opencv"
PACKAGECONFIG[rtspbin]              = "-DENABLE_GST_PLUGIN_RTSPBIN=1,           -DENABLE_GST_PLUGIN_RTSPBIN=0,                  gstreamer1.0-rtsp-server, gstreamer1.0-rtsp-server"
PACKAGECONFIG[smartvencbin]         = "-DENABLE_GST_PLUGIN_SMARTVENCBIN=1,      -DENABLE_GST_PLUGIN_SMARTVENCBIN=0,             smart-venc-ctrl-algo, smart-venc-ctrl-algo"
PACKAGECONFIG[socket]               = "-DENABLE_GST_PLUGIN_SOCKET=1,            -DENABLE_GST_PLUGIN_SOCKET=0"
PACKAGECONFIG[tools]                = "-DENABLE_GST_PLUGIN_TOOLS=1,             -DENABLE_GST_PLUGIN_TOOLS=0,                    gstreamer1.0-rtsp-server, gstreamer1.0-rtsp-server"
PACKAGECONFIG[videotemplate]        = "-DENABLE_GST_PLUGIN_VIDEOTEMPLATE=1,     -DENABLE_GST_PLUGIN_VIDEOTEMPLATE=0"

# GPU plugins -- videoproc
PACKAGECONFIG[vcomposer]            = "-DENABLE_GST_PLUGIN_VCOMPOSER=1,         -DENABLE_GST_PLUGIN_VCOMPOSER=0"
PACKAGECONFIG[voverlay]             = "-DENABLE_GST_PLUGIN_VOVERLAY=1,          -DENABLE_GST_PLUGIN_VOVERLAY=0,                 cairo, cairo"
PACKAGECONFIG[vsplit]               = "-DENABLE_GST_PLUGIN_VSPLIT=1,            -DENABLE_GST_PLUGIN_VSPLIT=0"
PACKAGECONFIG[vtransform]           = "-DENABLE_GST_PLUGIN_VTRANSFORM=1,        -DENABLE_GST_PLUGIN_VTRANSFORM=0"

# ML plugins -- ml
PACKAGECONFIG[metatransform]        = "-DENABLE_GST_PLUGIN_METATRANSFORM=1,      -DENABLE_GST_PLUGIN_METATRANSFORM=0"
PACKAGECONFIG[mldemux]              = "-DENABLE_GST_PLUGIN_MLDEMUX=1,            -DENABLE_GST_PLUGIN_MLDEMUX=0"
PACKAGECONFIG[mlmetaextractor]      = "-DENABLE_GST_PLUGIN_MLMETAEXTRACTOR=1,    -DENABLE_GST_PLUGIN_MLMETAEXTRACTOR=0"
PACKAGECONFIG[mlmetaparser]         = "-DENABLE_GST_PLUGIN_MLMETAPARSER=1,       -DENABLE_GST_PLUGIN_MLMETAPARSER=0,            json-glib, json-glib"
PACKAGECONFIG[mlpostprocess]        = "-DENABLE_GST_PLUGIN_MLPOSTPROCESS=1,      -DENABLE_GST_PLUGIN_MLPOSTPROCESS=0,           cairo opencv, cairo opencv"
PACKAGECONFIG[mltools]              = "-DENABLE_GST_PLUGIN_MLTOOLS=1,            -DENABLE_GST_PLUGIN_MLTOOLS=0"
PACKAGECONFIG[mlvconverter]         = "-DENABLE_GST_PLUGIN_MLVCONVERTER=1,       -DENABLE_GST_PLUGIN_MLVCONVERTER=0"

# Messaging
PACKAGECONFIG[msgbroker]            = "-DENABLE_GST_PLUGIN_MSGBROKER=1,          -DENABLE_GST_PLUGIN_MSGBROKER=0,               librdkafka mosquitto, librdkafka mosquitto"

CFLAGS:append:base = " -I${S}/gst-plugin-base"
CXXFLAGS:append:base = " -I${S}/gst-plugin-base"

EXTRA_OECMAKE += "\
    -DGST_VERSION_REQUIRED=1.20.7 \
    -DGST_PLUGINS_QTI_OSS_INSTALL_INCDIR=${includedir} \
    -DGST_PLUGINS_QTI_OSS_INSTALL_BINDIR=${bindir} \
    -DGST_PLUGINS_QTI_OSS_INSTALL_LIBDIR=${libdir} \
    -DKERNEL_BUILDDIR=${STAGING_INCDIR}/linux-msm \
"

FILES:${PN} += "\
    ${bindir}/* \
    ${libdir}/gstreamer-1.0/* \
"
