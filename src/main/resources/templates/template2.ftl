<#-- 实际高宽为 50*8=400, 40*8=320 -->
<#-- <SIZE>50,40</SIZE> -->
<DIRECTION>1</DIRECTION>
<TEXT x="30" y="30" font="12" w="1" h="1" r="0">${boxSn.boxSku.sortingTo!boxSn.boxSku.location!""}</TEXT>
<TEXT x="270" y="60" font="10" w="1" h="1" r="0">${boxSn.serialNo}/${boxSn.pcs}</TEXT>
<TEXT x="30" y="60" font="12" w="1" h="1" r="0">${boxSn.boxSku.deliveryTo!""}</TEXT>
<TEXT x="220" y="150" font="5" w="1" h="1" r="0">${boxSn.boxSku.pickUpCode}</TEXT>
<QR x="30"  y="100"  e="L"  w="6">${boxSn.code}</QR>
<TEXT x="30" y="250" font="2" w="1" h="1" r="0">${boxSn.code}</TEXT>
<TEXT x="30" y="270" font="2" w="1" h="1" r="0">${boxSn.supplierBoxSnCode}</TEXT>