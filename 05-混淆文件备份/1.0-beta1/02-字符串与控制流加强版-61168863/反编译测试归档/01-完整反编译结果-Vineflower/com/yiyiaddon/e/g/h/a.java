package com.yiyiaddon.e.g.h;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.i.g.c;
import com.yiyiaddon.m.b;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import net.fabricmc.loader.api.FabricLoader;

public final class a {
   private static final Path a = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3u0xlerxo0272","fEt293i7TqlEMDWLkpwdUJfRILiyz8xWCHKC80Zn3q52e4t78TsLagG/TrL1og==",2213396398044166554,-494104149273441810,2967987057445272850,-1208319906861854618>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s16skgqaussdza","oM+4wQlVq4fQgLyL3rNFCJnPrjvlE9CnHS2YOOSV7hPrwwatlmASeW+D",-1390753953944217921,5900112016231301065,1613997917291708073,-8142306420528106369>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s11l3t706pkust","u1KC+EVrurHtHAD/pQlToH1QpSVwkhXxloK6wc9Uxc47yD8R/a4kKk81MDolS6PtjeY=",1537592025554128542,1133366028123191489,6392611226495816760,2143950617340533889>()
      );
   private final Map<com.yiyiaddon.e.g.e.b, com.yiyiaddon.e.g.e.a> B = new EnumMap<>(com.yiyiaddon.e.g.e.b.class);
   private String jV;
   private String jW;
   private Float a;
   private Float b;
   private String jX;
   private boolean f;

   public void C() {
      this.B.clear();
      this.jV = null;
      this.jW = null;
      this.a = null;
      this.b = null;
      this.jX = null;
      this.f = true;
      JsonObject var1 = com.yiyiaddon.j.a.a(a);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s43gb22lnz68f","xopvcHstM5lcqtCRk1spFOamOrZfDXW7ZkGw0PuBkRg=",-4806217233490500048,-3909119674909533255,-8287616542184500498,5978636001588776647>()) {
            case -544614280:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.g.e.b[] var2 = com.yiyiaddon.e.g.e.b.values();
         int var3 = var2.length;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s29uas4msr47nm","59DqvlmbAAxVn5cN5iAQTzf3GeRiY6nednHuIjKMKmU=",6745778485265981239,4355167527447514275,-4940353969740600205,-1193964400689929168>()) {
            case -1638401454:
               while (var4 < var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2qm76209npc3y","fLfETBVOgjSQ58DgiyjGBmQuegHAULhhlPnCa6aeTRY=",-3156368699337884054,3587203056510181586,3189008081387367051,4437972645024636659>()) {
                     case 1119383167:
                        com.yiyiaddon.e.g.e.b var5 = var2[var4];
                        JsonElement var6 = var1.get(var5.aN());
                        com.yiyiaddon.e.g.e.a var7 = a(var6);
                        if (var7 != null) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s9sxumsk5ml2k","umIjJRumhV4OPxUXwKEncNKYyH4OoZ2+PRdHnRijD58=",-5576201479266488082,944359055707658820,7505072473217534900,7685040438179244983>()) {
                              case 817552032:
                                 this.B.put(var5, var7);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1lqzxkr5x7ki","bKXOaNaFlcty1xrcCKMovyNF5vzj1rBmHTbR12nFOrg=",-4924726326996074310,8781262110272512920,-6133927371557392844,-232728848156761103>()) {
                                    case -2025938723:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s25vx21zlm8mpr","A0NiLgXy0Y5DjhIpGnT58wYK3VzHw0l743+5kLol5c8=",-197420922097655503,-1568500093815670135,6466482685568713187,3689185353878841102>()) {
                           case 1744240726:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.a = a(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"s7um1h9ykkcaq","LmAX0eLS7eMH5QHJDCsghC/LvoJ4uYO8zA1mOE7XBSDcTGWFjOqv/ripBNtz5yY/",7337398155650038944,-789270171820151319,-3050501626122372501,-3548679829941301787>()
               );
               this.b = a(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"s30nz0vlve8g1h","SAeQhjtfgIhWGXEnhU9rkTCVqQjSJNM02eV+8YasRCDnsCaob/2R/D82C3NA0LbULv9ioQ==",3330120560069381767,4486099112410980379,-3034130862847425649,-5121207227322529364>()
               );
               this.jX = b(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"szpfslbxvrhcn","JaFRRzMN8O17UXOhwiuL9dyoj4lT7XF0JtCL5DtGeGi6hlFa5jo+Go6x5MGR4iCtsE7GQFi6WW8=",7227339427968245101,-792852820566864711,-8574598371747096421,-7611851380166797355>()
               );
               this.jV = b(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"skall92uzuljc","xZuzJo86Ok6OKCAmIxFLSwJ9eMxGvouEYSKb37pbseJQXeLSmWF1GD+X6RB2tEXoc9A=",6496020794465928033,-9207458000718323609,8624371520741894221,-4441265807684028278>()
               );
               this.jW = b(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"spp16injndq1","8wcZhUeygzn/4CpLaQzJx2dEAZzSSV3CH66kdQoY0LumHhVV7UgXZ6AHy7/ojhp5iVwCS4dA5UA=",-1951344271271906238,3205921365665816331,4707866504058314140,-2904564511372054306>()
               );
               return;
            default:
               throw null;
         }
      }
   }

   public void cy() {
      this.B.clear();
      this.jV = null;
      this.jW = null;
      this.a = null;
      this.b = null;
      this.jX = null;
      this.f = false;
   }

   private void cz() {
      if (!this.f) {
         switch ((int)com.yiyiaddon.m.b.a<"s19k1mreydwli7","0qq67kCP4eQu1XHCkBBOepdhKjSmWwxiJDEEBLP+jE8=",4038595545446431357,3161257047376694514,4008463131314857858,-6230463665632973725>()) {
            case 1601105307:
               this.C();
               switch ((int)com.yiyiaddon.m.b.a<"s24rn4ghlgnu5p","mFaqnp2b6D3FrSjzv+OOgU1x600uw5tMGwGIQy+uhAw=",-4410479248695874459,5947253993121826510,-4532794303473677095,9058621840424792439>()) {
                  case -662701660:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.e.g.e.a a(com.yiyiaddon.e.g.e.b var1) {
      this.cz();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qt7yfxqahiog","tKD75S0ugyvSjSK5j9TiFcB8aBsT41AmzQ2i3n0fmYU=",-7359558189364101101,1085085040707763652,4270939865991578382,-904790972057318333>()) {
            case -1782897576:
               switch ((int)com.yiyiaddon.m.b.a<"sn40etioeiizn","ztV8kGwOoyawhjAFbJdQ4tm4ro/ZUD9zryrgccA4J2w=",-1772956726112390942,1604275309856577614,7483144778750273784,3464723522988859056>()) {
                  case 511570025:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.g.e.a var10000 = this.B.get(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s2igizt81q64g8","MPis1Rk9cKSJ/A96X3VgFVj3KycRhA43DxNX+n8eDrE=",-1560433315917434471,-3939261764673153443,6806432958994165290,8384174324754308397>()) {
            case -1900064484:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public boolean a(com.yiyiaddon.e.g.e.b var1) {
      if (this.a(var1) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ycr1vhtm7m7b","bIptwWW1Juwy/zDZWhyUOIY11PwjKymS0aFeCq+t5VE=",-2668524098740157629,3461396484478551844,8123326846294904667,-9147483791504392738>()) {
            case -1826592025:
               switch ((int)com.yiyiaddon.m.b.a<"s1rjm7y0rf6xyj","WL7z0AGkk5imhcqeKSm+G/0DYSFjnltZxfNR8wDZcLU=",-4599932477553758405,-7911320874708028611,-1324026816212034178,4199567427161690645>()) {
                  case -1626915614:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1ykidrnpydkd8","ijovoVpxUgJW7wczy3dp6J0mEEHcUmKUOk0PR2myjJU=",-1715000953079562979,5522229294425083620,2907313949037183918,7937651592818609242>()) {
            case -408981630:
               return false;
            default:
               throw null;
         }
      }
   }

   public int a() {
      this.cz();
      return this.B.size();
   }

   public void a(com.yiyiaddon.e.g.e.b var1, com.yiyiaddon.e.g.e.a var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10ev69gfy1oqp","U87jv+4spPL2mAFYQnR4f49k1RKfOshuZ+LlJymFtlI=",1682607117398175441,6000718913679122509,7150323082813421902,-6056969096912893155>()) {
            case 1970798943:
               if (var2 != null) {
                  this.cz();
                  this.B.put(var1, var2);
                  this.e();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s39kiieqkqtny2","rNiLTXqb0eePuwsVfeD++IkFRR5K7STaJ687CXPqxfc=",-3624388281796816986,5018580655385167043,895088424433303186,-1979350176171058071>()) {
                     case -618924086:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public boolean b(com.yiyiaddon.e.g.e.b var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s268ad42cc9bmx","hdN3o/4tNoKf3Fth6PBRSx8YJeaz3A0k/jYZmq2gQg4=",2171712429297807858,-2543339327686515013,-1349480282463568484,8566515442177389856>()) {
            case -988923407:
               return false;
            default:
               throw null;
         }
      } else {
         this.cz();
         if (this.B.remove(var1) == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2n8cu9uf745yu","u3GjbBO5jqB21x0cSFpgdKCCj0GcSqEePQ+9KjAL1tw=",8666420899484278456,1886768933695297536,8284004335946575329,-4804128171900694056>()) {
               case -1752889989:
                  return false;
               default:
                  throw null;
            }
         } else {
            this.e();
            return true;
         }
      }
   }

   public int an() {
      this.cz();
      int var1 = this.B.size();
      this.B.clear();
      this.a = null;
      this.b = null;
      this.jX = null;
      this.jV = null;
      this.jW = null;
      this.e();
      return var1;
   }

   public Float a() {
      this.cz();
      return this.a;
   }

   public Float b() {
      this.cz();
      return this.b;
   }

   public void a(float var1, float var2) {
      this.cz();
      this.a = var1;
      this.b = var2;
      this.e();
   }

   public String aQ() {
      this.cz();
      return this.jX;
   }

   public void N(String var1) {
      this.cz();
      this.jX = var1;
      this.e();
   }

   public void cA() {
      this.cz();
      this.a = null;
      this.b = null;
      this.e();
   }

   public void cB() {
      this.cz();
      this.jX = null;
      this.e();
   }

   public String aR() {
      this.cz();
      return this.jV;
   }

   public String aS() {
      this.cz();
      return this.jW;
   }

   public void f(String var1, String var2) {
      this.jV = var1;
      this.jW = var2;
      this.e();
   }

   public boolean aS() {
      this.cz();
      if (this.jV != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27qq640gsofh4","Jo0A3M51bNByhauWMiy3C6cuR75NctXDc5dyZRlmqGw=",3989073235229185243,7122797136093927908,-6691477559761416640,-4549488961088635300>()) {
            case 1926367819:
               if (this.jW != null) {
                  if (Objects.equals(this.jV, c.fG())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2396eq7mma2pe","Kg9Uw31FkEcK2xbtHtikFzIxCuSOUE/X+3EdqunYhag=",1800332129262459472,3274463811491991727,5129655637360688007,-7785971615975259033>()) {
                        case -999991562:
                           if (Objects.equals(this.jW, c.bU())) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2hl5ifc7zc4v8","Yyb3bU7Kg+YkBTe6axbK2YZEk54OwBacnjDcW7yhGQE=",-5225641754972789232,-684469135019104412,-720325038623507809,2153573875342827480>()) {
                                 case 2095872748:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1lmo9clxochd2","ug7N0enn8QBB6kNirjxT+PWKvcF745KqX+DLesomN8o=",1293803222660975865,-5593019269203038364,-6420870087946043443,-4224190225099598939>()) {
                                       case -456803063:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s19o0ljvllko3b","XQvxtFeuePfnukCifeJoX1rF4DIIA//waAYIgUaq2zo=",1667961067670132348,9089505054420007212,-5905028394603457242,5149510234133820669>()) {
                     case 1005756496:
                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s33dcsdd9cjidt","qYl/Nnx0pVFV43wgvbODtVWFsZYDa5PAlr8zJ6TQ+oU=",-1079707728069453965,-6318457430928696930,19048846178949022,-1742736733388773914>()) {
                     case 482988710:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   public boolean aT() {
      this.cz();
      if (!this.B.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"slf5iz5jfsisn","hotE+xwRUOmDM1vScL9iA9nGzRwiIz7dybGwO81f/Ys=",6239300154683542798,4283885682481760016,1006470553344160763,6450026829627301287>()) {
            case 741284362:
               switch ((int)com.yiyiaddon.m.b.a<"sub8iny06m8zr","TsW2b/dCegb6pNrsq7XBEglmhxah+uc75U+UYeb6gQM=",5316592641136227972,6962870066203036092,-5856694280485458577,7810110277848608034>()) {
                  case -1838403975:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1erpd2g8rjzz8","GQtQSBCEIscLwU1XFz7q+R1fUHo0JwIsMRcZQL0PNSA=",-4357954533840783685,-6785563817711777388,-1228042257300654546,2683455696481649564>()) {
            case -1271199782:
               return false;
            default:
               throw null;
         }
      }
   }

   private void e() {
      if (!this.f) {
         switch ((int)com.yiyiaddon.m.b.a<"s2s25be8w2nbe3","F1SZJWUteXvrmZh70Fsdsow96nhxM9i/Kwy42T2afzg=",3815809772766102028,-6007463932692123069,-7368215137391606129,-2164216427069179012>()) {
            case 1090399829:
               return;
            default:
               throw null;
         }
      } else {
         JsonObject var1 = new JsonObject();
         com.yiyiaddon.e.g.e.b[] var2 = com.yiyiaddon.e.g.e.b.values();
         int var3 = var2.length;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sohhyfs968rah","O3IXj/4biLOchOM8ItfYnvxi5xBxJx59e2+zZGtiPLY=",4744146605081461580,-9032340394641074074,6897175617011200929,-7039476514055922465>()) {
            case 666716116:
               while (var4 < var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s7iaf9pt21y4o","rtVS0KchLRfwTfCC/UjaRuRaoEMNdT3HmqjFHorfe9g=",-2851793283426418620,5197894141270300250,6833477754462793921,-1103320528912953033>()) {
                     case 850128496:
                        com.yiyiaddon.e.g.e.b var5 = var2[var4];
                        com.yiyiaddon.e.g.e.a var6 = this.B.get(var5);
                        if (var6 == null) {
                           label82:
                           switch ((int)com.yiyiaddon.m.b.a<"s2viw881fw292u","5bFR6VuPbIP+EJKTN+wuEaMKKk2cpNiqgyrrWw0F64I=",7042246647272458070,314410554436863257,86509530406353150,-2657492011710039673>()) {
                              case 797479132:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3b1f86zw2owpn","tJLJkslVgQtLFGfutUSTlkx7vwo1QrFY5oMEIpJ+86w=",45877154622167535,-7458055739614313207,4739915536792797455,2758117869707820600>()) {
                                    case 25184995:
                                       break label82;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           JsonObject var7 = new JsonObject();
                           var7.addProperty(
                              (String)com.yiyiaddon.m.b.a<"s1ybzfib1t4cwv","CmBC+7P1suQPKZnGVspHVNkLz3FdtmiAmlrjEy4b",-7848468658004407099,-3436130509125764143,7380111165667039183,5791611876110344223>(),
                              var6.aj()
                           );
                           var7.addProperty(
                              (String)com.yiyiaddon.m.b.a<"sp09sgwl0gxed","GuW2vKlX5Yy5nHHOIjSDfsZmoXjzpzTpY16vOTi/",-8068550797476170502,468274733672761107,-5845022036920170117,-8945968768678006290>(),
                              var6.ak()
                           );
                           var7.addProperty(
                              (String)com.yiyiaddon.m.b.a<"s37jol03tzeos7","rbxyqGIYtDm0YCDZobEZCZYYfnnwnMAEFTRARXdp",589063098228617841,-4884569566993631943,1502349424817362601,-6488173751524386110>(),
                              var6.al()
                           );
                           var1.add(var5.aN(), var7);
                           switch ((int)com.yiyiaddon.m.b.a<"s2grruhc84zhn4","VI9PdBu/XY0SeWwr89sCLVBG6r/DNd739x3BV6YjaKI=",330948754147024053,809515486240257032,-14707768713302492,8956070770748221557>()) {
                              case -1391894954:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2a9ja0hcqh75","LA0CgbmTO0QSmnpemhx4dFnXQvpNsGQzAxFZx40SIq4=",-9137598424647511648,3386667286993455626,-3779743089441720580,8273593891318747749>()) {
                           case 657653866:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.a != null) {
                  label72:
                  switch ((int)com.yiyiaddon.m.b.a<"s2jnxhwln6ps0o","LAPCgzePkKKF6HEdkXLJ1Y5e1vVAAl8GYC/G4wnEUxU=",4731420566870652343,-9182959016673347883,3426037509280082693,-8372303910530436167>()) {
                     case 675388099:
                        var1.addProperty(
                           (String)com.yiyiaddon.m.b.a<"s7um1h9ykkcaq","LmAX0eLS7eMH5QHJDCsghC/LvoJ4uYO8zA1mOE7XBSDcTGWFjOqv/ripBNtz5yY/",7337398155650038944,-789270171820151319,-3050501626122372501,-3548679829941301787>(),
                           this.a
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s2390z1bklkfbk","psXzCmyVny7WDzVAY5PkNCaqsJONTu/Ip0G9X0LjoRU=",3321675325349610571,-8673130849136139972,7085704148718993038,-5729108286147790590>()) {
                           case 1344061167:
                              break label72;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.b != null) {
                  label67:
                  switch ((int)com.yiyiaddon.m.b.a<"s2aj237y7h8rhz","4uKwTayoTNVp475F5k9sOhlH/PeBBbjhpRESEGRosqE=",-9213826272336115278,2456006312424336874,132312169212513965,3534047400523607283>()) {
                     case -973274369:
                        var1.addProperty(
                           (String)com.yiyiaddon.m.b.a<"s30nz0vlve8g1h","SAeQhjtfgIhWGXEnhU9rkTCVqQjSJNM02eV+8YasRCDnsCaob/2R/D82C3NA0LbULv9ioQ==",3330120560069381767,4486099112410980379,-3034130862847425649,-5121207227322529364>(),
                           this.b
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s235q4co7ujrnt","gLrdy8LOrU+Gp3KTo8tFicjzyOgtsfr4UkbcrtwCPb8=",-1704581840777616748,-6536438419190046441,-6675136720886874531,-4353379825167802834>()) {
                           case 2042415608:
                              break label67;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.jX != null) {
                  label62:
                  switch ((int)com.yiyiaddon.m.b.a<"sjwxdqejirepj","H1xZCeDwYiAnSioAq7eSyN5l5Io/Ki6GQ2FEDyXTDyI=",-8863003921443518127,-7386478903511240018,-6428082600270709323,85953673789894484>()) {
                     case 1033423045:
                        var1.addProperty(
                           (String)com.yiyiaddon.m.b.a<"szpfslbxvrhcn","JaFRRzMN8O17UXOhwiuL9dyoj4lT7XF0JtCL5DtGeGi6hlFa5jo+Go6x5MGR4iCtsE7GQFi6WW8=",7227339427968245101,-792852820566864711,-8574598371747096421,-7611851380166797355>(),
                           this.jX
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s1yyrt9jaejea1","ktvBwtXPZrDpOWnOfymDkdUqAzuZP/knZBKj3JhLPPs=",166111534556169143,-7797079336841530596,3294191574638229086,1292735260627940509>()) {
                           case 1507302529:
                              break label62;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.jV != null) {
                  label57:
                  switch ((int)com.yiyiaddon.m.b.a<"s2dm3l6av97sus","AB+kGmp9wyaM6jNP37Dz58lKH5gnz6xf8e3qeJ6AfK0=",2215066110475368981,5537292633561740672,4147007315512525121,1198144674960657600>()) {
                     case 542943743:
                        var1.addProperty(
                           (String)com.yiyiaddon.m.b.a<"skall92uzuljc","xZuzJo86Ok6OKCAmIxFLSwJ9eMxGvouEYSKb37pbseJQXeLSmWF1GD+X6RB2tEXoc9A=",6496020794465928033,-9207458000718323609,8624371520741894221,-4441265807684028278>(),
                           this.jV
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s21qwyezklel0g","W31brBGt/v6rvM4nT4mtJh3JgGxba5mCyC8E5StG4Bk=",7402667851193741792,-5282347627809362797,-2814701531771248969,-1972304103810197761>()) {
                           case -771713856:
                              break label57;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.jW != null) {
                  label52:
                  switch ((int)com.yiyiaddon.m.b.a<"s2evywx0llilzp","axtL20c4YqO1EpYR+0FIlFwCUsDDdgHoLqfCkQ+T3HI=",2778882208033246470,-8488470273936329908,-4459587588097367343,7608637516277006249>()) {
                     case 213707871:
                        var1.addProperty(
                           (String)com.yiyiaddon.m.b.a<"spp16injndq1","8wcZhUeygzn/4CpLaQzJx2dEAZzSSV3CH66kdQoY0LumHhVV7UgXZ6AHy7/ojhp5iVwCS4dA5UA=",-1951344271271906238,3205921365665816331,4707866504058314140,-2904564511372054306>(),
                           this.jW
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s3ekwa5t7z23bz","sHbpOQGJvyL9xsSxPgtONOE8RxHk3dSxuggKWJgs7B4=",-3312866555387431472,-1171404220908923463,5729162235242875048,-6826186091759897186>()) {
                           case -822925621:
                              break label52;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               com.yiyiaddon.j.a.a(a, var1);
               return;
            default:
               throw null;
         }
      }
   }

   private static com.yiyiaddon.e.g.e.a a(JsonElement var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1oughf76qrlbj","rp9xo3ZDZlErw3MRyz9lQaswhkOf2Ts0b1+EfRp4w0o=",-3687312397487598839,1953670841546047973,1795166989778743320,5559835571192900553>()) {
            case -1120472388:
               if (var0.isJsonObject()) {
                  JsonObject var1 = var0.getAsJsonObject();
                  return new com.yiyiaddon.e.g.e.a(
                     a(
                        var1,
                        (String)com.yiyiaddon.m.b.a<"s1ybzfib1t4cwv","CmBC+7P1suQPKZnGVspHVNkLz3FdtmiAmlrjEy4b",-7848468658004407099,-3436130509125764143,7380111165667039183,5791611876110344223>()
                     ),
                     a(
                        var1,
                        (String)com.yiyiaddon.m.b.a<"sp09sgwl0gxed","GuW2vKlX5Yy5nHHOIjSDfsZmoXjzpzTpY16vOTi/",-8068550797476170502,468274733672761107,-5845022036920170117,-8945968768678006290>()
                     ),
                     a(
                        var1,
                        (String)com.yiyiaddon.m.b.a<"s37jol03tzeos7","rbxyqGIYtDm0YCDZobEZCZYYfnnwnMAEFTRARXdp",589063098228617841,-4884569566993631943,1502349424817362601,-6488173751524386110>()
                     )
                  );
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1abbiiaxom8g9","o7IW6aoVim6g+8xGLQhDlb+xzglXfIkPsfumFEKBYvw=",-6209586281844119369,-3162872962714856538,-3293996967541371576,2663494973856644550>()) {
                     case 1971508786:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static int a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsInt();
         } catch (Exception var4) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static Float a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsFloat();
         } catch (Exception var4) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String b(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            String var3 = var2.getAsString();
            return var3.isBlank() ? null : var3;
         } catch (Exception var4) {
            return null;
         }
      } else {
         return null;
      }
   }
}
