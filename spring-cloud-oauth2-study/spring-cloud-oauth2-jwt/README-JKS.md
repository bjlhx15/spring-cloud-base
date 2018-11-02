# JKS
## 一、概述
    在已安装jdk的机器命令行下
```text
keytool -genkeypair -alias lihongxu-jwt -validity 3650 -keyalg RSA -dname "CN=jwt,OU=jtw,O=jtw,L=lihongxu,S=lihongxu,C=CH" \
 -keypass lhx123 -keystore lhx-jwt.jks -storepass lhx123
```    
查看公钥命令
```text
keytool -list -rfc -keystore lhx-jwt.jks  -storepass lhx123  | openssl x509 -inform pem -pubkey
```
公钥证书导出
```text
keytool -export -alias lihongxu-jwt -file lhx-jwt.crt -keystore lhx-jwt.jks -storepass lhx123
```

## 二、keytool详细说明
```text
-genkey      在用户主目录中创建一个默认文件".keystore",还会产生一个mykey的别名，mykey中包含用户的公钥、私钥和证书(在没有指定生成位置的情况下,keystore会存在用户系统默认目录，如：对于window xp系统，会生成在系统的C:\Documents and Settings\UserName\文件名为“.keystore”)
-alias       产生别名,每个keystore都关联这一个独一无二的alias，这个alias通常不区分大小写
-keystore    指定密钥库的名称(产生的各类信息将不在.keystore文件中)
-keyalg      指定密钥的算法 (如 RSA  DSA（如果不指定默认采用DSA）)
     DSA RSA                    DSA或RSA算法(当使用-genkeypair参数) 
     DES DESede AES      DES或DESede或AES算法(当使用-genseckey参数) 
-validity    指定创建的证书有效期多少天
-keysize     指定密钥长度
-storepass   指定密钥库的密码(获取keystore信息所需的密码)
-keypass     指定别名条目的密码(私钥的密码)
-dname       指定证书拥有者信息
    例如：  "CN=名字与姓氏,OU=组织单位名称,O=组织名称,L=城市或区域名称,ST=州或省份名称,C=单位的两字母国家代码"
-list        显示密钥库中的证书信息     
-v           显示密钥库中的证书详细信息
-export      将别名指定的证书导出到文件 
    keytool -export -alias 需要导出的别名 -keystore 指定keystore -file 指定导出的证书位置及证书名称 -storepass 密码
-file        参数指定导出到文件的文件名
-delete      删除密钥库中某条目 
    keytool -delete -alias 指定需删除的别名  -keystore 指定keystore  -storepass 密码     
-printcert   查看导出的证书信息
    keytool -printcert -file yushan.crt        
-keypasswd   修改密钥库中指定条目口令   
    keytool -keypasswd -alias 需修改的别名 -keypass 旧密码 -new  新密码  -storepass keystore密码  -keystore
-import      将已签名数字证书导入密钥库 
    keytool -import -alias 指定导入条目的别名 -keystore 指定keystore -file 需导入的证书

下面是各选项的缺省值。 
-alias "mykey"
-keyalg "DSA"
-keysize 1024
-validity 90
-keystore 用户宿主目录中名为 .keystore 的文件
-file 读时为标准输入，写时为标准输出 
```
## 2、使用
### 1、keystore的生成：
(1)分阶段生成：
```text
keytool -genkey -alias lihongxu(别名) -keypass 123456(别名密码) -keyalg RSA(算法) -keysize 1024(密钥长度) -validity 365(有效期，天单位) -keystore e:\lihongxu.keystore(指定生成证书的位置和证书名称) -storepass 123456(获取keystore信息的密码)；
```  
keytool -genkey -alias lihongxu -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore lihongxu.keystore -storepass 123456
回车输入相关信息即可；
(2)一次性生成：
```text
keytool -genkey -alias lihongxu -keypass 123456 -keyalg RSA -validity 365 -keystore lihongxu.keystore -storepass 123456 -dname "CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称), ST=(州或省份名称), C=(单位的两字母国家代码)"
```   
### 2、keystore信息的查看： 
#### 查看方式一
```text
keytool -list  -v -keystore lihongxu.keystore -storepass 123456
```
显示内容：
```text
密钥库类型: jks
密钥库提供方: SUN

您的密钥库包含 1 个条目

别名: lihongxu
创建日期: Oct 31, 2018
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
所有者: CN=aa, OU=aa, O=aa, L=aa, ST=aa, C=aa
发布者: CN=aa, OU=aa, O=aa, L=aa, ST=aa, C=aa
序列号: 638af4e3
有效期为 Wed Oct 31 14:44:23 CST 2018 至 Thu Oct 31 14:44:23 CST 2019
证书指纹:
	 MD5:  3F:53:51:CA:31:44:AB:06:D2:E0:FC:04:2E:A7:5E:B1
	 SHA1: F5:28:FC:EB:AE:9A:0F:06:D6:9B:5C:88:79:00:57:41:EF:58:26:27
	 SHA256: 94:C0:9A:43:64:45:5B:D3:E1:69:D6:39:F5:62:FC:CF:9E:CC:66:82:7E:CE:19:AC:DF:C4:D3:93:D6:8F:34:BE
签名算法名称: SHA256withRSA
主体公共密钥算法: 1024 位 RSA 密钥
版本: 3

扩展: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 81 CC AE CD D5 89 CA 10   41 08 B3 47 F4 57 11 5E  ........A..G.W.^
0010: D2 1B 3B 8F                                        ..;.
]
]



*******************************************
*******************************************
```

#### 查看方式二
缺省情况下，-list 命令打印证书的 MD5 指纹。而如果指定了 -v 选项，将以可读格式打印证书，如果指定了 -rfc 选项，将以可打印的编码格式输出证书。
```text
keytool -list  -rfc -keystore lihongxu.keystore -storepass 123456
```
显示：
```text
密钥库类型: jks
密钥库提供方: SUN

您的密钥库包含 1 个条目

别名: lihongxu
创建日期: Oct 31, 2018
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
-----BEGIN CERTIFICATE-----
MIICNjCCAZ+gAwIBAgIEY4r04zANBgkqhkiG9w0BAQsFADBOMQswCQYDVQQGEwJh
YTELMAkGA1UECBMCYWExCzAJBgNVBAcTAmFhMQswCQYDVQQKEwJhYTELMAkGA1UE
CxMCYWExCzAJBgNVBAMTAmFhMB4XDTE4MTAzMTA2NDQyM1oXDTE5MTAzMTA2NDQy
M1owTjELMAkGA1UEBhMCYWExCzAJBgNVBAgTAmFhMQswCQYDVQQHEwJhYTELMAkG
A1UEChMCYWExCzAJBgNVBAsTAmFhMQswCQYDVQQDEwJhYTCBnzANBgkqhkiG9w0B
AQEFAAOBjQAwgYkCgYEAnip2ODPGbAmCAqIKYQ+LpIpMvFmYW5S8EUVYajN5xI1Y
tzDujfiGMiEsWVzyZ0EzsA64rRA337odtULYFFxXJZueKXJZOZO1gP8c6B8pWRzW
qmaBKrY7hHrJ7iCidguRM3jM7cG3T1pEaQ87qcS/GlIbiZRuY+VOcUDv2bZD7WMC
AwEAAaMhMB8wHQYDVR0OBBYEFIHMrs3VicoQQQizR/RXEV7SGzuPMA0GCSqGSIb3
DQEBCwUAA4GBAEa00uxqQHpo+UD6IqQj7Q5Zxt4TSeqb2Tx+44JGuaOglhFYIHLY
NrdCS4iXrbKJOQU/VJHYZNGJgINzfPNh2WiBapKVU1S1jkh5yRUY/c7AUBV1QasN
u5stdj0EIwDqdjrha0V4y+W/LYPe6fowm5/OfFvWt2Zx+vf/Xn8+6zkz
-----END CERTIFICATE-----


*******************************************
*******************************************
```

### 3、证书的导出：公钥证书导出，导出的公钥无法查看，私钥不支持导出
说明：
```text
keytool -export -alias lihongxu -keystore lihongxu.keystore -file lihongxu.crt -storepass 123456
```
-file lihongxu.crt(指定导出的证书位置及证书名称)
### 4、查看导出的证书信息
keytool -printcert -file lihongxu.crt
显示：（在windows下可以双击yushan.crt查看）
```text
所有者: CN=aa, OU=aa, O=aa, L=aa, ST=aa, C=aa
发布者: CN=aa, OU=aa, O=aa, L=aa, ST=aa, C=aa
序列号: 638af4e3
有效期为 Wed Oct 31 14:44:23 CST 2018 至 Thu Oct 31 14:44:23 CST 2019
证书指纹:
	 MD5:  3F:53:51:CA:31:44:AB:06:D2:E0:FC:04:2E:A7:5E:B1
	 SHA1: F5:28:FC:EB:AE:9A:0F:06:D6:9B:5C:88:79:00:57:41:EF:58:26:27
	 SHA256: 94:C0:9A:43:64:45:5B:D3:E1:69:D6:39:F5:62:FC:CF:9E:CC:66:82:7E:CE:19:AC:DF:C4:D3:93:D6:8F:34:BE
签名算法名称: SHA256withRSA
主体公共密钥算法: 1024 位 RSA 密钥
版本: 3

扩展: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 81 CC AE CD D5 89 CA 10   41 08 B3 47 F4 57 11 5E  ........A..G.W.^
0010: D2 1B 3B 8F                                        ..;.
]
]
```
### 5、证书的导入： 
准备一个导入的证书：[创建-导出]
```text
keytool -genkey -alias lihongxu2 -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore  lihongxu2.keystore -storepass 123456 -dname "CN=xx, OU=xx, O=xx, L=xx, ST=xx, C=xx";
keytool -export -alias lihongxu2 -keystore lihongxu2.keystore -file lihongxu2.crt -storepass 123456
```
将lihongxu2.crt 加入lihongxu.keystore中：
```text
keytool -import -alias lihongxu2 -file lihongxu2.crt -keystore lihongxu.keystore -storepass 123456 
keytool -list  -v -keystore lihongxu.keystore -storepass 123456
```
-alias lihongxu2(指定导入证书的别名，如果不指定默认为mykey,别名唯一，否则导入出错) 
显示：
```text
密钥库类型: jks
密钥库提供方: SUN

您的密钥库包含 2 个条目

别名: lihongxu
创建日期: Oct 31, 2018
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
所有者: CN=aa, OU=aa, O=aa, L=aa, ST=aa, C=aa
发布者: CN=aa, OU=aa, O=aa, L=aa, ST=aa, C=aa
序列号: 638af4e3
有效期为 Wed Oct 31 14:44:23 CST 2018 至 Thu Oct 31 14:44:23 CST 2019
证书指纹:
	 MD5:  3F:53:51:CA:31:44:AB:06:D2:E0:FC:04:2E:A7:5E:B1
	 SHA1: F5:28:FC:EB:AE:9A:0F:06:D6:9B:5C:88:79:00:57:41:EF:58:26:27
	 SHA256: 94:C0:9A:43:64:45:5B:D3:E1:69:D6:39:F5:62:FC:CF:9E:CC:66:82:7E:CE:19:AC:DF:C4:D3:93:D6:8F:34:BE
签名算法名称: SHA256withRSA
主体公共密钥算法: 1024 位 RSA 密钥
版本: 3

扩展: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 81 CC AE CD D5 89 CA 10   41 08 B3 47 F4 57 11 5E  ........A..G.W.^
0010: D2 1B 3B 8F                                        ..;.
]
]



*******************************************
*******************************************


别名: lihongxu2
创建日期: Oct 31, 2018
条目类型: trustedCertEntry

所有者: CN=xx, OU=xx, O=xx, L=xx, ST=xx, C=xx
发布者: CN=xx, OU=xx, O=xx, L=xx, ST=xx, C=xx
序列号: 160cf68c
有效期为 Wed Oct 31 15:07:50 CST 2018 至 Thu Oct 31 15:07:50 CST 2019
证书指纹:
	 MD5:  A8:56:D6:65:18:E3:04:FC:77:D3:96:9B:F7:AC:83:F3
	 SHA1: A4:6A:26:74:E3:89:F0:2C:14:27:2D:73:48:E0:B1:57:FE:B8:D6:FD
	 SHA256: A9:6D:18:89:0E:60:BB:86:36:5F:23:B4:95:EC:5C:F7:28:67:AA:83:FD:26:FB:73:3C:86:D3:C0:60:0A:F5:D0
签名算法名称: SHA256withRSA
主体公共密钥算法: 1024 位 RSA 密钥
版本: 3

扩展: 

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: 3C 0B 78 5D F3 AC 4A 53   E3 34 0B DD C3 48 6F E3  <.x]..JS.4...Ho.
0010: D9 FE EA 33                                        ...3
]
]



*******************************************
*******************************************
```

### 6、证书条目的删除： 
```text
keytool -delete -alias lihongxu2 -keystore lihongxu.keystore -storepass 123456
```
 -delete -alias lihongxu2(指定需删除的别名)
### 7、证书条目口令的修改： 
```text
keytool -keypasswd -alias lihongxu -keypass 123456 -new 112233 -keystore lihongxu.keystore -storepass 123456
```
keytool -keypasswd -alias lihongxu(需要修改密码的别名) -keypass lihongxu(原始密码) -new 123456(别名的新密码)  -keystore lihongxu.keystore -storepass 123456
### 8、keystore口令的修改： 
```text
keytool -storepasswd -keystore lihongxu.keystore -storepass 123456 -new 112233
```
keytool -storepasswd -keystore lihongxu.keystore(需修改口令的keystore) -storepass 123456(原始密码) -new 112233(新密码)
### 9、修改keystore中别名为lihongxu的信息
keytool -selfcert -alias lihongxu -keypass 112233 -keystore lihongxu.keystore -storepass 112233 -dname "cn=lihongxu,ou=lihongxu,o=llhhxx,c=us"

## 三、openssl使用
### 1、openssl生成证书示例
生成一个rsa的pem证书
```text
openssl genrsa -des3 -out private.pem 1024
```
创建crt证书 pem 生成 csr
```text
openssl req -subj "/C=CN/ST=BJ/L=BJ/O=HW/OU=HW/CN=CL/emailAddress=xxx@xxx.com" -new -out cert.csr -key private.pem
```
自签发证书：csr,pem生成crt
```text
openssl x509 -req -in cert.csr -out public.crt -outform pem -signkey private.pem -days 3650
```
### 2、转换
keytool和openssl生成的证书相互之间无法识别，keytool生成的为jks文件，openssl默认生成的为PEM格式文件。
需要先转换成pkcs12格式，然后再使用对方的命令转换成需要的格式。
#### 1.1 keytool生成的jks证书转换为PEM格式
jks转p12证书
```text
keytool -importkeystore -srcstoretype JKS -srckeystore lhx-jwt.jks -srcstorepass lhx123 -srcalias lihongxu-jwt -srckeypass lhx123 \
 -deststoretype PKCS12 -destkeystore client.p12 -deststorepass 123456 -destalias client -destkeypass 123456 -noprompt
```
p12转成pem证书
```text
openssl pkcs12 -in client.p12 -passin pass:123456 -nokeys -out client.pem
```
导出私钥crt证书：从 PFX 格式文件中提取私钥格式文件 (.key)
```text
openssl pkcs12 -in client.p12 -passin pass:123456 -nocerts -out client.crt
```
#### 1.2 PEM格式证书转换为jks文件
pem转换为pkcs12格式：
```text
openssl pkcs12 -export -in public.crt -inkey private.pem -out server.p12 -name server -passin pass:123456 -passout pass:123456
```
p12导入jks
```text
keytool -importkeystore -srckeystore server.p12 -srcstoretype PKCS12 -srcstorepass 123456 -alias server \
 -deststorepass 123456 -destkeypass ${passwd} -destkeystore ServerCert.jks
```
## 三、证书说明
keystore.jks 也可以为 .keystore格式 ; server.crt也可以为 .cer格式
"-rfc" 表示以base64输出文件，否则以二进制输出。
（Java、.Net、Php）语言需要的证书格式并不一致，比如说Java我们采用jks，.Net采用pfx和cer，Php则采用pem和cer；
主要分成两类，其一为密钥库文件格式、其二为证书文件格式；
### 密钥库文件格式【Keystore】
* JKS:.jks/.ks:【Java Keystore】密钥库的Java实现版本，provider为SUN  
密钥库和私钥用不同的密码进行保护
* JCEKS:.jce:【JCE Keystore】密钥库的JCE实现版本，provider为SUN JCE  
相对于JKS安全级别更高，保护Keystore私钥时采用TripleDES
* PKCS12:.p12/.pfx:【PKCS #12】个人信息交换语法标准  
1、包含私钥、公钥及其证书
2、密钥库和私钥用相同密码进行保护
* BKS:.bks:【Bouncycastle Keystore】密钥库的BC实现版本，provider为BC  
基于JCE实现
* UBER:.ubr:【Bouncycastle UBER Keystore】密钥库的BC更安全实现版本，provider为BC
### 证书文件格式【Certificate】 
* DER:.cer/.crt/.rsa:【ASN .1 DER】用于存放证书  
不含私钥、二进制  
* PKCS7:.p7b/.p7r:【PKCS #7】加密信息语法标准  
1、p7b以树状展示证书链，不含私钥
2、p7r为CA对证书请求签名的回复，只能用于导入  
* CMS:.p7c/.p7m/.p7s:【Cryptographic Message Syntax】  
1、p7c只保存证书
2、p7m：signature with enveloped data
3、p7s：时间戳签名文件
* PEM:.pem:【Printable Encoded Message】  
1、该编码格式在RFC1421中定义，其实PEM是【Privacy-Enhanced Mail】的简写，但他也同样广泛运用于密钥管理
2、ASCII文件
3、一般基于base 64编码
* PKCS10:.p10/.csr:【PKCS #10】公钥加密标准【Certificate Signing Request】  
	1、证书签名请求文件
2、ASCII文件
3、CA签名后以p7r文件回复
* SPC:.pvk/.spc:【Software Publishing Certificate】  
微软公司特有的双证书文件格式，经常用于代码签名，其中
1、pvk用于保存私钥
2、spc用于保存公钥
 


