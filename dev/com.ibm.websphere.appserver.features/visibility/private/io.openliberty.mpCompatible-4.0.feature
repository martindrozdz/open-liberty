-include= ~${workspace}/cnf/resources/bnd/feature.props
symbolicName=io.openliberty.mpCompatible-4.0
visibility=private
singleton=true
-features=com.ibm.websphere.appserver.eeCompatible-8.0, \
  io.openliberty.internal.mpVersion-4.0; ibm.tolerates:="4.1"
kind=ga
edition=core
WLP-Activation-Type: parallel
