package com.yiyiaddon.e.n.i;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.trading.MerchantOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class h {
   private static final String sE = (String)com.yiyiaddon.m.b.a<"s2w434ovu5il05","qXXHe+NhGx0N6f9J9dMq7NYmIqDoQU6ukv1SgMJR38ZL1GvylV3yHtHxKpcZNGRpeVY=",2073875140764031772,-4364796755671898476,-2779549547637043726,-4718200872611411396>();
   private static final Logger m = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s3khzmjhmsnqvd","X2bSJPCxIeEBNw5nlbHQEwiDYAXLdihZ/phebPMg+d+pW4SgTKkk+JMD2juz7LLQ3fEYhPLPktxtnf7Tfgg=",1291451011371028861,-7247751268451543212,-7680674164595978706,-817176328947928179>()
   );
   private static final String sF = (String)com.yiyiaddon.m.b.a<"sdqyu7u57oyae","Q4EVueYxmFm5vwP/oV8jgwzBVkVdjJK+OySj5bkTqgRUjYgiYrQp+d0qkZKs6j+yTBKBhbLdP8zNwj4sL1Nm2OThikKwF2GlVsfv387hSR0=",-5497499699038170436,-1182915674237592068,-4909880436956094082,-5709474945889771618>();
   private static final int mj = 40;
   private static final int mk = 3;
   private static final int ml = 600;
   private static final String[] U = new String[]{
      (String)com.yiyiaddon.m.b.a<"sfzbzytikacl1","xenoBqde55yef89K0bpjkh51dDKjV21lJNVnGARBs3aLRBag+1XLfM13g6Z7g34LHLXPJK7QOyimtvoxUxHIyVJBuV4PiR6h6luuvbMd0KlMtnJcOr5XUg==",210204200404689503,-5349434250686087671,-2841090257615303045,135036042266119406>(),
      (String)com.yiyiaddon.m.b.a<"s2upmp21y6xxxv","Kutlz5MQD1p0fIN4We2+Fa7rid1Z0qt7721G77FnAd9LzZJSijk098F3M7pPWOsN3LfwRqQkdGITLUGqPkOZNl3P7Rkx3Kd+xU4DnLf5TRo=",-1187174523750187649,-1956648350179980786,-2186089692788685094,-2886483039856385430>(),
      (String)com.yiyiaddon.m.b.a<"sisp4cxbug2c4","8H2yOsmwGBzNrcB2UdptFug5m/SL1RgwSUXyLeGkyXvf/5GhKkxfXug+egQFoxxDAYpNondbOpQKlqdksq+//ttS4lsdgHM29/g/cYt0x+j+XAbGyieHJNYIBuLvBg==",-698243063589706116,-8017658895061065493,-6779371534825902771,6849606400809533385>(),
      (String)com.yiyiaddon.m.b.a<"s3jydvp6elk6vh","oiaOJML2J4ct2O4ylWwOtUezq2V+D26tIZ+c6MD/5O9o8KPI0bseA/SX/iInx5oaztRi3ieOo38GviFRAooaA4JJSjOgP7d2lydG8l0YvDIwbpS01SInJg==",6335099128371291362,-8658694480232009816,1998083435175301491,-7997005528339841792>(),
      (String)com.yiyiaddon.m.b.a<"slodlmmulsyef","AcDXatQREF6+vmoH++U21NDzdbjwaqC4cwzPs+v9+AlYOkQISXIrCn2SIxkisBTbzwxASy3F5ghRgpRUKPKB95Etn2JeIaI1sM3HrDT71rHCgg==",7859033274351020459,1298195051292558556,8965006765544719880,-3023068966882610723>(),
      (String)com.yiyiaddon.m.b.a<"s2my1sl2gm8ubu","MCEvY3nxvj/J1qVdIogokNhtJOhlYXvCnIEDsWB6DvHEvG6zuZdqsSrCPM4jana/p/djR5cd1vNSjYwifoIx8N+5dhKGzA==",100483996040344059,7002049717912299623,4401168879470336995,4757285254585763884>(),
      (String)com.yiyiaddon.m.b.a<"s1xioe319kc8le","2NEKCqow0zFhQbdnU6i9Vt1mB6PSVNAYJWuuHnJg5kCING3HxAjgbb4Cd06yG723UtL6uCoSDyyPEgp8l+aMzwik",6130154422101461327,-61728885241432809,-5467035985322400062,8728340036644382212>(),
      (String)com.yiyiaddon.m.b.a<"s1n106nxtdobb7","Xbb89SQTJMUufCQlP+3buFXxoumWudrOeAJtNSPJSmSgmozjwNSMGCbhiiCLwg==",6686342888598335323,4585829243588834976,8861825491848911488,-8139434546551986766>(),
      (String)com.yiyiaddon.m.b.a<"snuixw0450y46","2xzqRIUS51yZENO0szPJUeUuOydbtuD2NQ7thThVnmoLdfaqmhsSQbD5L9Y=",-5948499277651004847,-6366744184459842866,4165839640556110033,1191554528873704929>(),
      (String)com.yiyiaddon.m.b.a<"s2krfzbac0kwyf","hGtnXlEnSHvm4FpzHuEakqdt1s6Qks3+Z17+SJMAT9ibtA88XLE=",5345397826343344892,-3393797966217623250,-2335673666148869676,-4031633933585378730>(),
      (String)com.yiyiaddon.m.b.a<"s29sokg7csup5z","2TfUFZrnWU7p/atDaQD51IRMxcEj4w4VpGLsk2a+ekAQhDJkLuvGpQ==",6816161583435327867,-5079023194863215568,4030080501068604634,-8345262332096992629>(),
      (String)com.yiyiaddon.m.b.a<"s1cvs9grvgg50s","SFfXKFmvnPf+IwWPUOozfIFXYFx7TEIjaQKx13P7xYbtxQdO",-1653296515145361153,-3880837413007736525,-737516991131967420,3818235674132636589>(),
      (String)com.yiyiaddon.m.b.a<"sylotou7z3q1n","9HevHotX4Ry1WO+m88qNlCaxs9hJTTKZaD1hFk0YNHX4muke",2101398536225361049,7911811224752236638,-4002824604038288279,-7165560168436763553>()
   };
   private static volatile int mm;
   private static final Set<String> L = new LinkedHashSet<>();
   private static String sG;
   private static final int mn = 64;
   private static final double am = 8.0;
   private static final double an = 16.0;
   private static final int mo = 256;
   private static final int mp = 3;
   private static final int mq = 2;
   private static final int mr = 1;
   private static final int ms = 1;
   private static final Path i = Minecraft.getInstance()
      .gameDirectory
      .toPath()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s15qcez1w77hf9","CXSeebagMgkNk2UmlSgnkkbpH4Jg0Tm02/RjMA4Qxfhr600FWSOiIg+pfRvkW9GVLus=",-6228271825047104115,9221408775873015451,-4760656943736625475,2236159839929240248>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3vrlqftx9hcf1","CscX65UvuWwtvT7lfBMCVGZY4HZqy5+gFWLM+OI3nv3VFUP24COAuHxdhZIOa2Gw",1763397084146684139,8203900957256183582,8072252388313023650,-1326551943170317935>()
      );
   private static final Map<String, String> U = new LinkedHashMap<>();
   private static final Map<String, Integer> V = new LinkedHashMap<>();
   private static final Map<String, String> W = new LinkedHashMap<>();
   private static final Map<String, Set<String>> X = new LinkedHashMap<>();
   private static final Map<String, Set<String>> Y = new LinkedHashMap<>();
   private static boolean bx;
   private static boolean dH;
   private static String sH;
   private static String sI;
   private static int mt;
   private static boolean h;
   private static final List<Runnable> bM = new CopyOnWriteArrayList<>();

   public static int bZ() {
      return mm;
   }

   private h() {
   }

   public static synchronized void init() {
      if (!h) {
         h = true;
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"sdqyu7u57oyae","Q4EVueYxmFm5vwP/oV8jgwzBVkVdjJK+OySj5bkTqgRUjYgiYrQp+d0qkZKs6j+yTBKBhbLdP8zNwj4sL1Nm2OThikKwF2GlVsfv387hSR0=",-5497499699038170436,-1182915674237592068,-4909880436956094082,-5709474945889771618>(),
            com.yiyiaddon.d.a.c.TICK,
            var0 -> ae()
         );
      }
   }

   public static void g(Runnable var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ksos8jz39dj3","erCYal4wuYF0hAvUh8LPDhOLwhLX5BtYZ5Jpx1znMaY=",-8909365030762470886,7538283898438254639,-8968281027541874521,8204165941330948029>()) {
            case -371554965:
               bM.add(var0);
               switch ((int)com.yiyiaddon.m.b.a<"st0iu13pig9p4","isSgxRQo5m73ZI/3DcYRpWWpnXwksm4uNHw5c6iPQ90=",3311896824643761017,-1277976833630446214,3749227322083291973,1959099479374896398>()) {
                  case -1031236537:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static String aE(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7tw3ni065npu","WRRIoFUb1W9aKM+DYwqjeKed7CWCo6Ur7bScrtOmrx4=",-7456461634764733572,-1931952197447103990,-5302809142708490343,6706941715545206422>()) {
            case 1248878782:
               if (!var0.isBlank()) {
                  cz();
                  return U.get(var0);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s9ys95286gvn2","L48+36S6oIPIHEARxYzpTWuY8ldD0gq3WEDnuND2STU=",-2776215778975379744,5650084032681636402,1843733514565853663,-8126837677184563812>()) {
                     case -1648522629:
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

   public static Map<String, String> m() {
      cz();
      return new LinkedHashMap<>(U);
   }

   public static void c(ItemStack var0) {
      a(var0, true);
   }

   public static void d(ItemStack var0) {
      a(var0, false);
   }

   private static void a(ItemStack var0, boolean var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3789nt57h9nwm","zArlK8ucYW6njrrEjQmOn51J+z7L7RVgG8tlJdnqpBM=",307898599568053,2062830516852883882,-4516780503296542473,-6537341471544626657>()) {
            case -1386518276:
               if (!var0.isEmpty()) {
                  if (var0.get(DataComponents.ITEM_NAME) == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sh03znmig0lhg","NsmVTXUQTUEDbdHOY3BO9jq/48BXj7ayJT+6aUoUnH4=",-4796070882505562208,-7003558912909179896,-6919322187364189983,-5380205352424385526>()) {
                        case -583847306:
                           if (var0.get(DataComponents.CUSTOM_NAME) == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"saogxrlqz40n7","W+pmm+uGWw6djLemU9rzGheJFZHscQcSwNodBQ2Obnw=",8014088309783552207,-2678670697496704410,8871754470127112523,-961193668562884022>()) {
                                 case -1475736792:
                                    return;
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  cz();
                  String var2 = c(var0);
                  if (var2 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1ev6kq6av9xjw","LG/Gz/kfaCde2kOE1XmlJaik3Q6oNlTfcwgMQMcpO9g=",-3764333541689001595,-789622726701422197,-6423881725673066540,7646555719125342538>()) {
                        case 1868732877:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     String var3 = aj(var0.getHoverName().getString());
                     if (var3.isBlank()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s341o7tdsvdpty","ZDdppd/D2CeSVqv83SwbaVuKu7apaCkX3Dq4xfkF/8Q=",-6196494738050008162,-7725954586282685606,8436666776533771267,7893905652444503432>()) {
                           case 1418666884:
                              return;
                           default:
                              throw null;
                        }
                     } else if (var2.contains(
                        (String)com.yiyiaddon.m.b.a<"s2x1y3s1d5xg3d","bv+7h8qQqP1tPjPa9UCj0dBXFfkm1wSgFpYeK3cAJgIZ4/9fCYAS/mCm",-244047649238884996,2798882504593308456,9133966196356665030,603754781315472259>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s7zgdopznnz3y","qfydpvB/A+CJcO8ZUTY+sMg/couQBVfOboWOuU0g4tU=",-1131958442629854566,-289889057268146176,-552070286775494616,44798906697463950>()) {
                           case 721714340:
                              av(var2);
                              l(
                                 var2.substring(
                                    0,
                                    var2.indexOf(
                                       (String)com.yiyiaddon.m.b.a<"s2x1y3s1d5xg3d","bv+7h8qQqP1tPjPa9UCj0dBXFfkm1wSgFpYeK3cAJgIZ4/9fCYAS/mCm",-244047649238884996,2798882504593308456,9133966196356665030,603754781315472259>()
                                    )
                                 ),
                                 var3
                              );
                              return;
                           default:
                              throw null;
                        }
                     } else if (var2.endsWith(
                        (String)com.yiyiaddon.m.b.a<"s17pv9vx2caxvf","crsDL52/2IzBNFXxCeO2nMzoygRNoQllIAWkfsPb8lXNB7p1AmuEGA==",-7607651844402622289,7490287945741543436,3087683511863834116,-5815673191309480244>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1q6ljtyr3ywyj","b/ezGrHBujF6kMSDe6FZE9gsGlECgcphjlEuiq/vlZ8=",-1717867148540116966,-6443608914464244171,-1701152073072788101,2097076797570665506>()) {
                           case -1920089683:
                              String var4 = var2.substring(
                                 0,
                                 var2.length()
                                    - (String)com.yiyiaddon.m.b.a<"s17pv9vx2caxvf","crsDL52/2IzBNFXxCeO2nMzoygRNoQllIAWkfsPb8lXNB7p1AmuEGA==",-7607651844402622289,7490287945741543436,3087683511863834116,-5815673191309480244>()
                                       .length()
                              );
                              String var5 = aG(var3);
                              if (var5 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ojgw02g7p5pr","Q/7JXoaRX23rsz4OEcj4B0HMg1HGfX/KvOOp2i8pNGA=",8435992691353078165,588148545901432036,8376288456281592700,2432338033193232425>()) {
                                    case 1240560588:
                                       a(
                                          var4,
                                          var5,
                                          (String)com.yiyiaddon.m.b.a<"svkt1a3qm2sjj","0aqwyOH9j8SMXrtRFaGWiSIx/zGnT7IkzJoFbZJEFQHVWw==",-6046540908880102598,-3557187044932504837,2759309329274046680,581220517175283321>(),
                                          2
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"syslsevndynyk","eS6bYY6szd3SgyfxHvL7k4YuUdIoQ4/tFLustKCFcy8=",-7764930867545365385,-5246401958969992771,138829399309895292,503638716143440620>()) {
                                          case 1326484095:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return;
                           default:
                              throw null;
                        }
                     } else if (aa(var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2upiydx3qx8sw","6EZ3HiychuFqew5yEu5iF55OYWbzp2bjdmjArUH0riY=",-2690540409372230378,-6279783043903453154,-6439991184217477453,-6919391046548976644>()) {
                           case 1862427769:
                              return;
                           default:
                              throw null;
                        }
                     } else if (var1) {
                        switch ((int)com.yiyiaddon.m.b.a<"suws3waddon9u","+l+sVFNArcwbQITTsQsPtWce/lr1Ti1xKh3FiyoPyhQ=",-4468933414657236013,1820150578217555061,-7425177358308991513,-8241040950107945757>()) {
                           case -1828446845:
                              a(
                                 var2,
                                 var3,
                                 (String)com.yiyiaddon.m.b.a<"s36osyft268i5q","DEqJhFuuzmRkOCf4cyDvvIns353nEjtNV9g6TkthJq2txw==",6545743117849333636,6445676699237763362,5523908508189509387,-698680742668548280>(),
                                 3
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s1zm3z267jl5c9","df7BrhV/t5Tiz1In35ht4KNzwY1EuRaVWozs1IV9Sog=",4930130101931114453,-1342418679455945038,602406144312931826,-1367131396404523733>()) {
                                 case -930310330:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        a(
                           var2,
                           var3,
                           (String)com.yiyiaddon.m.b.a<"s25aobfvvn2ptb","d+x2UiyDZaxJpBGkFg6LpBTCdKvGRJ9qaoBOf83BgGOfYQ==",4833701990904646755,2752023582295590264,3849277356315563240,-8226808377995175627>(),
                           1
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s379dxqsxtq5z9","C6hkRhvbjfqcJjPkQXoWrOmfx72Hs11mhgRD8BtPBtU=",-2820606779994731686,-1058494177194340491,7250997521751485075,-4721512830691772484>()) {
                           case -1026719363:
                              return;
                           default:
                              throw null;
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3lks18i7z94yf","i+xElMJyxUnkwvcfcu5wEFg4c3chaQpVUUQy4jYjDIE=",-6203104335620490792,6105415333904232116,2908741532360127519,-9173292382088718472>()) {
                     case 624539695:
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

   public static void gB() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1p76kwtroxism","SZIvtj843n/z4Kd4WSBgZnz/fD6rxhl8VUEkGoI2HP0=",3463007746909126403,-6966133033770332773,-7982146746038752511,6395048996947682813>()) {
            case 559037369:
               return;
            default:
               throw null;
         }
      } else {
         Inventory var1 = var0.player.getInventory();
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1j3lmcrm5mteg","M9D8Sv6NDNC5NDyfIkaphhNdi/DKVLGozFEfhOh8Tpo=",-8573875409342744030,8886561772174590729,6788679398736278804,-7782118488565752071>()) {
            case -761549178:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"su991m8opebf8","gtIGHXmEUJQhIqL9iLheojABZ+1wRuRtyw0uiK6Jwoo=",-6353240714992942686,-807980180126952845,829935275015628485,-1173860374180965171>()) {
                     case -1172237512:
                        c(var1.getItem(var2));
                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2c3ztqiu9ddx0","MBQ4yL1QYN5N0Jt8Hp/ebdX5lUdg/r06GncThFjVvTg=",2766457404630937475,8623376071082867668,-13253986323997408,6573137706217267912>()) {
                           case -2102678085:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               c(var0.player.getOffhandItem());
               if (var0.level == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1hau9gpsotbl4","UO5877hu1ZqkwJo/7WtOjO16+X5xAve4Wvhp6HGKkIE=",2799305699187721934,5283940410796848689,5402621312846457742,4012682300258268128>()) {
                     case 760901129:
                        return;
                     default:
                        throw null;
                  }
               } else {
                  var2 = 0;
                  Iterator var3 = var0.level.getEntities(var0.player, var0.player.getBoundingBox().inflate(8.0)).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2z4x9j83sm5zh","6p9J2ExwYJ1RFKSwLMI6eDEO7R5GP8ghDObbW7tFQ+8=",-3026364391047028782,-7817085659971562105,-7632615811330890789,-948989142928059535>()) {
                     case 569870248:
                        label105:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s27zdye7lnfs0n","yUDrXfJvGTDKQAZDwygkCapa7QN2V5QaSOqMxQU7lC8=",-29009854627071955,-8357733351742528673,-3458971722211404149,8682066921335237152>()) {
                              case 1110451361:
                                 Entity var4 = (Entity)var3.next();
                                 if (var4 instanceof ItemEntity) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1jcxwk8bnak3j","40PaZbRTlJCu7f1Foms9eloukAyKc3KgRy0rqoVRJjg=",-4870876363506719368,4899933244665120711,-5275077347807165168,5692130819481438282>()) {
                                       case 215979589:
                                          ItemEntity var5 = (ItemEntity)var4;
                                          c(var5.getItem());
                                          if (++var2 >= 64) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3jdfuv2v5pzec","tBEzf4y1cEnbo8waO9adF5gybwHjSyvtRwFYUoloTXg=",6082648304128286596,-5399283022068763844,-3105488536640450505,5014401735543484838>()) {
                                                case -780604446:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2l2thm4dw2qqf","91uwz8CFAgDscO/kidQB+/Nm0SZRsXJEAD2uzGKYqs8=",6354902654595334207,2554746880888798411,1055729547269292678,2282919849021312563>()) {
                                                      case -1941477279:
                                                         break label105;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2lp9yca41bpnl","+Mw8Pcpa9wHwK/6uorvCv9QwYhrWlcYf2cLLI9mf0Hg=",-1240415424119177971,2779558362128919422,5572801632839528310,1613604771758870262>()) {
                                             case 1568929365:
                                                continue;
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

                        int var8 = 0;
                        Iterator var9 = var0.level.getEntities(var0.player, var0.player.getBoundingBox().inflate(16.0)).iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s3b8oarnzkhxde","PEwWYRX55EQyKQP2+68Jd9Qr25cA5ffZfozE7tIe/UM=",-2393281705450568166,-7017016510248766445,5880240421894700663,3350675847368091199>()) {
                           case -1949810537:
                              label93:
                              while (var9.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3cpglvojs98wv","jvtPIcykmONt1wZf9QC03J5Hw2WtwAThrX8y3snAyHI=",6065256795506963797,914149199042777314,-3752483398922905693,-2004585880062226800>()) {
                                    case -1943445103:
                                       Entity var11 = (Entity)var9.next();
                                       if (var11 instanceof ItemDisplay) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s103vpid8ra4l2","2b8ugkWUk8lum8dQRoyqE494dga+HC4fI2LP5wRqKAo=",1365800143100690380,-7220408697486103266,3353181090942748582,-386706521720636207>()) {
                                             case -125540976:
                                                ItemDisplay var6 = (ItemDisplay)var11;
                                                c(var6.getItemStack());
                                                if (++var8 >= 256) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s6jqwp05lrgqx","JWLtOXsHsSq2SKkMWL9rf4XlTfxjLLTfiXVDfrgTOio=",1204785004845891351,-2251243426113238678,-1422761674064425734,6438281427711630201>()) {
                                                      case -431719286:
                                                         switch ((int)com.yiyiaddon.m.b.a<"sardsm7t53d3z","NbLQpZfZfLiKc0D3Te60qUYwqZ4GVxFaX/X3g0oSRDY=",-4312705693877748994,5126170900095440269,2556941519322326010,535299671381387625>()) {
                                                            case -1017868975:
                                                               break label93;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s3vx340e7ef6tw","E8JqPE1UBFlr0YPaz4ivo12EWysRMLwvx55SWvzmfJw=",-7010211234690170732,-834478353550787096,2044384534902906925,-4560884743311565452>()) {
                                                   case 1692218055:
                                                      continue;
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

                              AbstractContainerMenu var10 = com.yiyiaddon.i.a.a.b();
                              c(var10);
                              gC();
                              b(var10);
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static void b(AbstractContainerMenu var0) {
      Screen var1 = Minecraft.getInstance().screen;
      if (var1 == null) {
         label43:
         switch ((int)com.yiyiaddon.m.b.a<"s2gqzd0xydqj0k","wsoaL0Pb8ka4Rdgt6nxedzSc7CTCCJ6A7Ki5MN94lsI=",2805288124044565822,-420190453325470443,-4854881146509156622,-6654864464167294089>()) {
            case -1422780202:
               sG = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1oznc95zfj7j3","8WjvMDFLUh/Jsi3Xt8UDImq4G9paEnVKswZoQvbI2TU=",2743525191866393228,-4515707188067504389,8915143126515670842,4442953830613637843>()) {
                  case 392167292:
                     break label43;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         if (var0 != null) {
            label58:
            switch ((int)com.yiyiaddon.m.b.a<"s2o522uw94lxtp","GlSLEweEXWq+byDIxVvRDWE1aVhF1GcZbb2a3SjLLKk=",8129548764948178980,6589542655926805718,4216582779661011503,-2932546633349102515>()) {
               case 483006169:
                  Iterator var3 = var0.slots.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s24zhgqgi1pnqm","GMSyjTIxtAex/kYbMIG95t8X2NswVsOa7l2lOT7AXfE=",-8223980186812500980,-6001344845665420384,-681934405289616067,7353875212058641698>()) {
                     case -907079325:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sy8hswpdjauhq","KkMAb+opPZZP5EnGmGJnIhPkiL6z36zyJWYAONcdirk=",1715271842978402085,-5768046339757168799,-1513281519358148157,3937821124185269126>()) {
                              case -1090764589:
                                 Slot var4 = (Slot)var3.next();
                                 if (!var4.getItem().isEmpty()) {
                                    label52:
                                    switch ((int)com.yiyiaddon.m.b.a<"scgma97q3y1z7","VW9Y2fYrtnVZ3cDW3zc644BZ/aUy9fM052ZjXaW224o=",7793336730721419470,-5309815068070059769,5028878051496418630,5901783450736820885>()) {
                                       case -333382157:
                                          var2++;
                                          switch ((int)com.yiyiaddon.m.b.a<"sokbybiixo84c","6EQwzbD+ktifSYmMj4hJSU9wcywvUzD1CjSELx0Iorc=",183089833828032505,-3335774804030037483,7872872219071812607,648534540464703127>()) {
                                             case 134834855:
                                                break label52;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s20vl1hcweh254","+GeSwnk1OckNovKOSlAOu8en/f3HGz/KBolmYxGkG6c=",1283828043937878750,-6398397410367452224,4510720487886649851,-1935846930267241778>()) {
                                    case -854665735:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label58;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String var5 = var1.getClass().getSimpleName() + var2 + mm;
         if (!var5.equals(sG)) {
            label46:
            switch ((int)com.yiyiaddon.m.b.a<"s372rxqdu9bmkc","s8QCmUZ4x0mw3ffs2rO1oBIuM3YlfSbmDnplhDzDCio=",8245784522966262381,188817358746104226,-3505033376100228297,-4441189408011447949>()) {
               case 2120739224:
                  sG = var5;
                  m.info(
                     (String)com.yiyiaddon.m.b.a<"s1ysmi50isz97v","B9QJH0zmlCOC8p7zrdk1tOwXAN99z5O6S5VyqwKY0DylEx5C0lOynn+2NTpuTMb8NWe5yP8AOKl5Z5WNh6EjRbVfopCoHD2JAMVOs59roGFbVPKYJz8e7ID5DR008uuVbRoz/2RMLoVAEHPM2D/2rsXo8+s=",-7589586768103793369,1793463356200317042,9169453135060868116,-8449966746529767396>(),
                     var1.getClass().getSimpleName(),
                     var2,
                     mm
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s3qt7pjhjukqod","jr3UZ65rTRCz/YLiF688oZEZ/0kCbBaMj2OqkBFgS9Y=",-3287763412918784729,8733671178845024770,5650474711609612696,5940310844709810334>()) {
                     case -1932068172:
                        break label46;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }

      if (!L.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s22l8ra6z9xejz","i93u4OtZfq1aUaC7f+spJXYLEr9uZOjET58+Y3ttg+E=",4497261109783806768,8008646162491363840,-3890653351736280465,2670274197060973326>()) {
            case -1625720748:
               m.info(
                  (String)com.yiyiaddon.m.b.a<"s2pro2i98yca7x","BFla5pi03YkAg3VUDTKNqGor0KVVQXYgpR/UdpGKVRx582brm0RPdtchab3h23MdaRuLhvCxholuz4FHaiudisJRMnFz7byBiPiKAp8tECZQxZA6LBTL5crFqRk=",4377412218769820494,8110276349288211072,-5230925937535315834,-8589827178955034708>(),
                  L.size(),
                  String.join(
                     (String)com.yiyiaddon.m.b.a<"s22yr0bh8qpb8b","kTF4evQo9Jaq5lSB4jMI4aDEMlRYUAq7SucU7rg6",-8265905916561266552,-430327889043775116,1138602039306074841,-5591702159992125287>(),
                     L
                  ),
                  U.size()
               );
               L.clear();
               switch ((int)com.yiyiaddon.m.b.a<"s386fedhfi1u94","ntP89r9sjN/ClV6IwSBTRSxyDQobpQwlsj09I8dFI5Q=",8079489944606985077,-20910755253971443,3302011219547599370,-6311071062298780970>()) {
                  case 933914387:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static void gC() {
      Screen var0 = Minecraft.getInstance().screen;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pkryf0yu8taf","x1gfrKP0lJi9tuv+wyYU5VaBFfTRo7YEGddLpsWi9Nk=",-3192441287150397044,1839374192602478404,5127523690051027911,-6245709606991953761>()) {
            case -1231429085:
               mm = 0;
               return;
            default:
               throw null;
         }
      } else {
         mm = 0;
         a(var0, 0, new int[]{600}, new IdentityHashMap<>());
      }
   }

   private static void a(Object var0, int var1, int[] var2, Map<Object, Boolean> var3) {
      if (var0 != null && var2[0] > 0 && var1 <= 3) {
         if (var0 instanceof ItemStack var15) {
            var2[0]--;
            mm++;
            d(var15);
         } else if (var0 instanceof Collection) {
            for (Object var22 : (Collection)var0) {
               a(var22, var1 + 1, var2, var3);
            }
         } else if (var0 instanceof Map var13) {
            for (Object var21 : var13.values()) {
               a(var21, var1 + 1, var2, var3);
            }
         } else if (var0.getClass().isArray()) {
            int var12 = Array.getLength(var0);

            for (int var17 = 0; var17 < var12; var17++) {
               a(Array.get(var0, var17), var1 + 1, var2, var3);
            }
         } else if (!(var0 instanceof String)
            && !(var0 instanceof Number)
            && !(var0 instanceof Boolean)
            && !(var0 instanceof Enum)
            && !(var0 instanceof Class)
            && !(var0 instanceof Thread)) {
            String var4 = var0.getClass().getName();

            for (String var8 : U) {
               if (var4.startsWith(var8)) {
                  return;
               }
            }

            if (var3.put(var0, Boolean.TRUE) == null) {
               for (Class var16 = var0.getClass(); var16 != null && var16 != Object.class; var16 = var16.getSuperclass()) {
                  for (Field var9 : var16.getDeclaredFields()) {
                     if (!Modifier.isStatic(var9.getModifiers()) && !var9.getType().isPrimitive()) {
                        try {
                           var9.setAccessible(true);
                           a(var9.get(var0), var1 + 1, var2, var3);
                        } catch (Throwable var11) {
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void c(AbstractContainerMenu var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ags633psjgy8","c9jYM88/49VvGxWv8TZ9nFWbJAGWkLSauWGgyl8e+UA=",-4123815718430704997,6067639148500033412,-2696827162841095426,-7370285764318838549>()) {
            case -1516255442:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var1 = var0.slots.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"scqgi8h7w7yvh","N5FN7NkEJSqfqmJK3T7QjtN5HR5bcLcOyLeN9z5c44c=",6897142233501146925,-5326598418218887205,-1284877464675255180,-6734015763285995763>()) {
            case -1394680713:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ktocg6lsdoij","qa13gZyJxpNpBMrTjihIcbzuij8EcPr1yBhvyleJQ58=",2862200514081017046,9079137189399974000,-6062534118694604180,181070463328735385>()) {
                     case 1442310433:
                        Slot var2 = (Slot)var1.next();
                        if (var2.container instanceof Inventory) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2amc52lzm409","ZTgLT6J/YHEnP5rU59/DtW3d0tXAA/SE2DgX0cRB5aU=",2363465888515021168,5042168564783204178,2366729974376795618,-1705097500469168956>()) {
                              case 478304608:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ap3p01knrbfg","D1Oy6baxzpaS/PMhPG8Dl9aluouwQJn0cJ0O5sG6HI0=",6615503098706313791,-1977588181525693794,3145143264968375321,1236023333715639261>()) {
                                    case -1843006664:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           d(var2.getItem());
                           switch ((int)com.yiyiaddon.m.b.a<"s1cnjbj0kjrbjy","oQFBr/FTAiCiTmo1WK1urkMHfxj+YdVNgR1jqKm+8qQ=",-6928798748238114298,8716279544866662382,-7060161497249947839,-4909974733116628649>()) {
                              case 430154628:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               }

               if (var0 instanceof MerchantMenu) {
                  label35:
                  switch ((int)com.yiyiaddon.m.b.a<"s2jp3mvbwx8zln","yTaGSraI1xZJfY/vnUscgsAFNfIKQ5pjj8EPbv6G/V4=",-2613201717039767829,-5174998861936038660,-6601662753724007805,6099754908642219665>()) {
                     case -1995613575:
                        MerchantMenu var4 = (MerchantMenu)var0;
                        Iterator var5 = var4.getOffers().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s2d85ifz2j4sg6","rhcq0N2ipUiHquxE4LU7Onp2WBo8izgBDdHYz6JIdJM=",-7641271643162717808,445634841131289183,-7394263612158672946,-4233920512223034234>()) {
                           case 939697192:
                              while (var5.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s23m362t7u5q5v","DiqkkQzGfAfA4cFzTr48DZgDQMzJNotOwvVCywb582A=",924667000012311934,1553386793961834069,8023473630349609405,6958355071088259366>()) {
                                    case 391550918:
                                       MerchantOffer var3 = (MerchantOffer)var5.next();
                                       d(var3.getResult());
                                       d(var3.getBaseCostA());
                                       d(var3.getCostB());
                                       switch ((int)com.yiyiaddon.m.b.a<"s32pidlk6bria3","QeO9oe9VQAYMWFXhDKCazIssXhSVwV4LHQbIKJpBOUk=",3756759186637062537,4232988660355548189,-5287978976947240131,-4973788542572403370>()) {
                                          case 1801725814:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                              break label35;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   public static void gD() {
      gB();
      if (!df()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uttwsezaacxc","ytyUcclME0xYJfdpwzMLRDbLcbZI/M3lR+5wAJA1n8U=",5755757444331978698,-5753215663808528219,2912859288481468885,-760445065516453133>()) {
            case -1384981457:
               return;
            default:
               throw null;
         }
      } else {
         gE();
      }
   }

   public static void f() {
      if (dH) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1y9b12a17k1eg","fHzpyYy7XV0NaVOtVjxB3wMWP7Q/UpdZqsfPkiU18fI=",4302757829283814478,1752058196264757876,2625357590278689535,833308041861423029>()) {
            case 943630911:
               dH = false;
               e();
               switch ((int)com.yiyiaddon.m.b.a<"sgbd37vtvxfry","7cwu9B5qqC2wGE9wxDCUO2eXlNRgxlV+cjqd0MKAiMI=",5909217336302430589,-8587281723253921643,-6159972648990549541,-7365641760667866548>()) {
                  case 1506148962:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      sH = null;
      sI = null;
      U.clear();
      V.clear();
      W.clear();
      X.clear();
      Y.clear();
      bx = false;
      mt = 0;
   }

   private static void ae() {
      if (--mt > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3e0jmnbw4xv28","Oen474JsGbDi2Pb8CZv6zj5nNrbRLCS6ZCPdHcjWTIY=",-8428050362120944278,-4599451261027009419,3425551814698147006,-4422378567217424808>()) {
            case -442365453:
               return;
            default:
               throw null;
         }
      } else {
         mt = 40;
         gB();
         if (dH) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s1zohc3euzwdz7","rVlB0nkNCsAi1j4DT5DVr7xsxeyFkgTk3p0nb+wbHWw=",-2487736062978528426,5123781555412353539,-8135565716846065034,1311575329370096406>()) {
               case -1933727126:
                  dH = false;
                  e();
                  switch ((int)com.yiyiaddon.m.b.a<"s2m3xo2sats3xb","p5MmmpA8pkLoXB0ZwDMmm6EJAPDnnKzFl7mlr081IrY=",4926324045916905702,6850416826352563759,-2228638377827042641,-4043999931792076206>()) {
                     case -1062081076:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (!df()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1yrxdwhkpsxrc","Z3vJaDvYaqMMZSPows5/U3tckF+b3n+x3Mw3qTyG6go=",2465909885530440272,-6720304495990294441,-1743012957502499965,-3785233495140931171>()) {
               case -1582986607:
                  return;
               default:
                  throw null;
            }
         } else {
            gE();
         }
      }
   }

   private static void gE() {
      for (Runnable var1 : new ArrayList<>(bM)) {
         try {
            var1.run();
         } catch (Exception var3) {
         }
      }
   }

   private static boolean df() {
      boolean var0 = bx;
      bx = false;
      return var0;
   }

   private static String c(ItemStack var0) {
      String var1 = com.yiyiaddon.e.n.p.a.d(var0);
      CompoundTag var2 = a(var0);
      String var3 = com.yiyiaddon.i.b.c.a(var1, var2);
      String var4 = aF(var3);
      if (var4 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kjwfmdje65dy","USzC0kATqFH0Th0lWYBnO/Nm8UCARikMfSCPv9uJq/c=",8517170587155693763,-1115867328826294847,6666861417286216310,3560485940388546455>()) {
            case -1235485406:
               switch ((int)com.yiyiaddon.m.b.a<"s1kkesdhhk8djw","9nJAGt1lRD01B77iQCFfE4S8Bx9Uawl/HhXo6bq0trM=",-8025063793938924212,941371081781485827,8441527275637116687,6961909910740959130>()) {
                  case 531100970:
                     return var4;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = aF(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1xzzj1tbnhoey","7Q6XHNU4S3NnZYbBQEjv1fH7HNXYxyUtX8f4oRXbqKY=",-7652135630494897026,-3882776857674959003,-8987855379084924337,1248826539113726260>()) {
            case 1807037560:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static String aF(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s113z1gcmtledl","JbzWk5/rE00Hn+sLsogXKotRRCiVxMDQOwkHjCkt3P8=",7513538536622028726,-2154031719765180239,-5794803705236648572,14496798686314085>()) {
            case -298021912:
               if (!var0.isBlank()) {
                  int var1 = var0.indexOf(58);
                  if (var1 > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"ss9dliooekrey","0yHgqE7sF+knF67MddulkDTVMNqxGsH5MWjcH13jbe0=",4525847775165159440,-765738635023669473,-2768580974679818591,4966124348972000402>()) {
                        case -556067249:
                           if ((String)com.yiyiaddon.m.b.a<"s2w434ovu5il05","qXXHe+NhGx0N6f9J9dMq7NYmIqDoQU6ukv1SgMJR38ZL1GvylV3yHtHxKpcZNGRpeVY=",2073875140764031772,-4364796755671898476,-2779549547637043726,-4718200872611411396>()
                              .equals(var0.substring(0, var1))) {
                              String var2 = com.yiyiaddon.i.e.a.bS(var0);
                              if (var2 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2bq1zqcjnanws","OybCNcVLmgkXQoucL9+Mob7ubtgiNsnktF6RtVY5E+0=",-1943494579509360242,469436368565256773,7990983427799118701,259789135569904575>()) {
                                    case -1248412836:
                                       if (!var2.isBlank()) {
                                          int var3 = var2.lastIndexOf(47);
                                          String var10000;
                                          if (var3 >= 0) {
                                             label50:
                                             switch ((int)com.yiyiaddon.m.b.a<"sh4cwfewgkgd5","yvN/z+0rj95HsjI2zEG1ZHXTj5+8nbTkxDjB/Kuu170=",-6234638027149890965,-773198419625776772,-5571018371381404553,-319640246903612390>()) {
                                                case -1226792498:
                                                   var10000 = var2.substring(var3 + 1);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3bxel6og8blf1","c++t6SzIa1Nc1jdSM0m2DDZmgwH73G99Ycl27DLKOOk=",-8431607331548077005,-6612939864531587408,-5804932457919322236,-7467474556491489302>()) {
                                                      case -894788338:
                                                         break label50;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var10000 = var2;
                                             switch ((int)com.yiyiaddon.m.b.a<"s11gd274053tra","H0oOegetYHHBQXZ6nCmAHwDxwT3S2ANPMg+5/DvI5nQ=",6211488347372539158,5246201760594932509,1830936240731209420,-2718743304728265487>()) {
                                                case 362443812:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          String var4 = var10000;
                                          if (var4.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2gkrsfa4v1fe8","n6AdhWaRTeNKiNEI2z+vnGakcLVZKTmAVP6X34T3Sjk=",-2103442213744403514,5277465822463341107,706014861977580392,8985591931314632419>()) {
                                                case -209074340:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1irvfirpu51wz","DRJvPhQQcBUr8cmMSkuWuqNueYcx/q2ZXQYQlcY09tU=",-3234856191461420354,2932309357102543898,-2385945031856858331,-4202608621636813179>()) {
                                                      case -875678376:
                                                         return null;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             switch ((int)com.yiyiaddon.m.b.a<"s12qkvo84c4tj3","ILqFO1mK+dKLLE8MqXpU5cyumu4hCet09VZmzj4KIB8=",5670044228186955808,-597728860446389448,-3660470262213764497,-5440453690215941212>()) {
                                                case -1308439716:
                                                   return var4;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s36w0jxjcw29pf","GNL7orh6YspT2SxICDPrKspu7kPTvQO8d8GnD14JVDo=",8117679597363884022,-7504377646480710172,-7162190895510185175,5472483017514490307>()) {
                                          case -1712680927:
                                             return null;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return null;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2tb7dji2gq0eb","2yfGfqnypAGYBiH7ZtrMydcJicaxcSBpBz6XbkERoOo=",-2459273549371536836,-7459619468676822332,-8550985898229354619,-2108907972035518365>()) {
                              case -766452199:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ldr3y2k2ddfz","hpsqD4Pe3nPFDXOrr4cCirhC5C7ARLYHVGMmpAq5seI=",-6683852615872677284,5643896563746040475,-4615969744428622347,-7191113064893718990>()) {
                     case -761350332:
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

   private static CompoundTag a(ItemStack var0) {
      CustomData var1 = var0.get(DataComponents.CUSTOM_DATA);
      if (var1 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s3sb4imw5148ba","r/9+7mNxwQzuC4xPGaLzpGJP1Mzq6kdHeJIuww34teY=",7050136956054674940,-41275215912794116,4524618937476276275,-2742188501220642054>()) {
            case 1151399084:
               if (!var1.isEmpty()) {
                  CompoundTag var10000 = var1.copyTag();
                  switch ((int)com.yiyiaddon.m.b.a<"s2jan67whv8wdv","6KTWe7DbvnO6PONcPotodk3fR9A0IiDwe1IfO7be7DM=",6883166146649171940,-8108227906614528293,-1901317957711272989,4727686924185125641>()) {
                     case -645618486:
                        return var10000;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1gr86bqvkc9g8","w652Wnu/bFJvguwq68vNN7PdStIWvwgpVtHM2Oe6cwk=",3793716773142809129,3979587007294840648,-3067103531898380112,-6833542594400662450>()) {
                  case -1316180404:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s3ci8rxvluuwfw","0sR9PSwvAs9pqd59ETwtpJ0Fe/gViztk1fsDOjM8BS8=",-797204143877840701,8824008475534433615,-588103297549041432,-4854766835119020234>()) {
         case 1020917452:
            return null;
         default:
            throw null;
      }
   }

   private static boolean aa(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      if (!var1.endsWith(
         (String)com.yiyiaddon.m.b.a<"siuq80gllwosq","BFwqgiiNIuVsasT6am4aq6b5q0OBzi7NtxGyKOYQoRvWMBOcUlDqywHSfPfaptI2bBtiBA==",4010926192458822268,-3008378610078348237,8396138011240800048,1549354501952709110>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s6x70a0p92b23","GujvCBmNuNmhiTGV6go09/BEoO4RZ4sSzFqdbl48xk8=",9148747505491279208,7137888773819659852,1629275835776417610,-6147311079180254942>()) {
            case -592433317:
               if (!var1.endsWith(
                  (String)com.yiyiaddon.m.b.a<"s3tnnob9anulyy","rYvYKCu0cHNq4yVjAhSRpvKxblwGrDxBHqkyU75VdyJG7wgFqfenm415xWvEVpt5Kkaacg==",-3982165714233971413,-1505125979655154578,6162253669429130768,-2097526907990192413>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s34i9ywbl60bs2","ExV/xFbAbvqi32HUewWe87CdJgaDi+e0TqD9JGv0MDo=",-2653245731430010199,-5938718152350304737,231877383220475869,5886754507605808772>()) {
                     case 1332717005:
                        if (!var1.endsWith(
                           (String)com.yiyiaddon.m.b.a<"s3eixah5bmed9u","tuplE1e0CHuffO8emShnQcEcEZ4LsglpfW5irPaMPANFJeI51eY0PtJ4PsBysTBv",7746360598036257290,-4176770888876507017,2261890591376094536,1011050819724784328>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3c4ubckph78hc","52Or1650IXpRqU4ZMwtuCTssfn09h5xtf6RQGHTjJDo=",-7087569804556584136,-5161650864439442560,-2778547962293670314,-5478596414477751641>()) {
                              case -240983498:
                                 if (!var1.startsWith(
                                    (String)com.yiyiaddon.m.b.a<"s2ia5fdx23xn27","1AjG3QcZEhqcrO+rvjnZ5jnMc4gNtvFweBOtBULGsrZqpDlW9Tbi/Vij",-4449377160814817270,-2784339214050652897,-3518096022439970763,-4047685349289132015>()
                                 )) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3lfnbor6ka339","THhE6gIcscCRCFV+62Fa/jnIEueAa89g/WUezrH7sB8=",-9033160944601180803,9113924814624200259,-4185150435449013536,-8145599805373398354>()) {
                                       case 760079805:
                                          if (!var1.startsWith(
                                             (String)com.yiyiaddon.m.b.a<"s2twdi533ekr90","xfyINDI5ubzGI50ezmxmCGLPkGoImBJWeFa4Bta800SlZPBqGUd1Lg==",-2305103094148981911,4776618445514295334,1508307808877820319,-5626892564584604120>()
                                          )) {
                                             label34:
                                             switch ((int)com.yiyiaddon.m.b.a<"s242e0hpqbutw8","zftvupPvQGCeqPdKTuo5ZWrPGJxRedwH/7HJXy4Cjiw=",1150764941485557504,-7706799554333876019,3243355411502119351,1595098505156347444>()) {
                                                case 250558678:
                                                   if (!var1.startsWith(
                                                      (String)com.yiyiaddon.m.b.a<"s3149e5bapfoo9","5WKUrFpiIXeFZ7g+FFZ9KlGiSyQRcOAUOWtT5/oUbO2i9jHybJsEeX3WTz87OQ==",-290312205237529446,-6563701132790691276,-6007663410143207089,-6120255274664224483>()
                                                   )) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2x9ssayrfaiw7","468m+TkiDvLd0pBv1r6E/NVYQ/VTKgQeKBN6THsZl30=",6959802807375938268,-5922579266253186461,-641531213772389172,-4139845310438239450>()) {
                                                         case 1784287666:
                                                            return false;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"sbcbt8n7we63m","adTc8Jktpx1Cg3gnMqHOYvlWD05r4tRjV99CZ3jx0MQ=",4047929662014731629,5290237232277250283,8926678833341355129,-5662529308650600856>()) {
                                                      case 1365753966:
                                                         break label34;
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
                                 break;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2rjtmaufuemug","Pb3261laOOjArKNMjgTZY6FebLGpvBUd314VpCiHpVk=",-505488291751984965,1315606144953274368,-2701738792799229143,-2751204836387761978>()) {
         case 748140248:
            return true;
         default:
            throw null;
      }
   }

   private static void av(String var0) {
      String var1 = com.yiyiaddon.e.n.j.c.ad(var0).toLowerCase(Locale.ROOT);
      String var2 = com.yiyiaddon.e.n.j.c.aP(var1);
      String var3 = com.yiyiaddon.e.n.j.c.aQ(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vodsvv6m5wg2","nfIvGPN46QwhAGBVEiT8OvOHLgulRc+++6+cRDucu4Q=",4363308160695688609,6928178147067473141,-644039444797369389,1961430264549369186>()) {
            case -202502206:
               if (var3 != null) {
                  Y.computeIfAbsent(var2, var0x -> new LinkedHashSet<>()).add(var3);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1od5upmgnmh2v","FrOSPcbKv0wx7Pex2HcNFtwlpjNqe7tLomOSMVlSB6E=",-4211540999895485882,-2261443613863932005,5079166348684442336,3526940662674006700>()) {
                     case -1443594707:
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

   public static boolean h(String var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s36kdtvybb7nr","kyW9+vptQPXFvet0he+qCUaUJZx4gJBtYyHRNyzQj/A=",4119565227237608905,-4078012616121159138,-4432389683851683379,-3218729683074603325>()) {
            case -1318359248:
               if (var1 != null) {
                  Set var2 = Y.get(var0);
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3t25zmgzuzst7","1KVToaFiHhS1RalDbo4MmkJfNL0FubsksHW/looKtpw=",-6726686831557043908,8503299141905234992,1255413441856556373,-1844840557841104436>()) {
                        case 1439133896:
                           if (var2.contains(var1.toLowerCase(Locale.ROOT))) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3kp993zbdde7i","TqlqQNhBedEykh+LSCGzDFqIgkv9/ttkYBCsodo2DFk=",-3866316728423162325,-2734110883920198637,-6405756815896790362,-5624403090320048496>()) {
                                 case -1883868433:
                                    switch ((int)com.yiyiaddon.m.b.a<"sorll65ji23u9","hPd/eFV637iazQFPND2AwAJ+3/YBJEKp/bMp8EnLb94=",589220479346424145,-2558280967177375642,6218461218104403065,-4540062802062560255>()) {
                                       case -1808687487:
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

                  switch ((int)com.yiyiaddon.m.b.a<"s3r875uwpehu3j","HNL0i53rWlA9i2Qz4y6I+cZ86u6uwLRW1W1/6AKR51g=",2275710322203760268,-3566686457850164500,894822908154498014,-1304036331526358798>()) {
                     case 777483909:
                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bhimqmy0cdpj","o2ZgfMLOsRAJJ7esuLTgfMOqoSoTF+OqwwWIpMXqxBQ=",2503938613250983644,-1725995732545755994,-6255295419361384578,-698663989442523265>()) {
                     case -845432293:
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

   public static List<String> e(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x48nbsp0a1dx","LsfuZc6Z/oX9QILrWpYH4m0wPJhBLhcb+kYrVtHibcI=",9109737380261332186,-8177637351012143877,-1330386082938491391,-2433458952347093906>()) {
            case 346256418:
               return List.of();
            default:
               throw null;
         }
      } else {
         Set var1 = Y.get(var0);
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3cikfdy0obt3l","Wwivtiu65NAE3jQqkpU1TjwW9kkJ8ve4thhY5bAra7I=",4871094503782533834,6390071335244070681,2453635420804829208,7205645080737246652>()) {
               case -1114911477:
                  List var10000 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"sryz4z9y5ml5l","qlGEX6VE9qGwg6aLQXtpQBicEsaMzAoMP5gQs9oFEcE=",3555115117498623348,-1998772157996492701,8346723773858350980,5009434146333479499>()) {
                     case -512870394:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            List var2 = List.copyOf(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s33h947uwzpcea","74AoDefBHbeBumy2IN/YAXcOVPNu7NVvsW2ljjBv0FE=",2288553999303212136,-4571204568665974699,7246259664551229938,-2294857869751042255>()) {
               case -196840634:
                  return var2;
               default:
                  throw null;
            }
         }
      }
   }

   private static void l(String var0, String var1) {
      if (var0.isBlank()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q6ze36gvy4x5","+Ez3CjvwNeiwXHl3mfwthxbwX8lAEm3SMg9TLpzu3To=",-2765590931557743394,-6436576835392321430,1178583041340992515,3795899581966139619>()) {
            case 1599914515:
               return;
            default:
               throw null;
         }
      } else {
         Set var2 = X.computeIfAbsent(var0, var0x -> new LinkedHashSet<>());
         if (!var2.add(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3tvu7u0fkjksv","LQIq0RlO+4ggVUpQKztiomo+BbVnea69DbE33KErPMA=",2128104185577806665,4020633964117434545,5576508104482721690,-8829360191705656667>()) {
               case 304310040:
                  return;
               default:
                  throw null;
            }
         } else if (var2.size() < 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s27wdlagzzqjip","hYGFopIglQ4m30VyNCM3jQVU1hkB/OzQlUOx0W4W45A=",4253479990306467274,3112982896885259198,-1067575200663537412,-4680404754654583046>()) {
               case -472873315:
                  return;
               default:
                  throw null;
            }
         } else {
            String var3 = a(var2).trim();
            if (var3.isBlank()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2wyim2w9ifm9b","afwo7t82sE8Oogtt17tgr08vRqhpTfkDeGLZw6ziid8=",-8333467217402077515,8363917960131512894,74284774849019920,4888682967570378409>()) {
                  case -421761871:
                     return;
                  default:
                     throw null;
               }
            } else {
               Iterator var4 = var2.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s2du53da61lbyk","VJlQtZ2FyyLz2RgYN4AzVHPu14cCLCLBBDefXfbcfTw=",3861298048031021282,-2098849118732624503,-3357406993927902982,2549858475332590593>()) {
                  case 1070063541:
                     while (var4.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s41kis7mtzpia","S7DkELSZM5cqEnujMPJHdaSPM4oyKJTG/bDEaDddbok=",1095635053963268961,8714301363783891921,8187876829351564450,5647045164649149579>()) {
                           case -1283370456:
                              String var5 = (String)var4.next();
                              if (var3.length() >= var5.trim().length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2g9plex00q8fr","/k9GOtyYmyrTnUzQw6xuMseCk+ZuJ57nlydUa573qJQ=",-3575601442575418393,-7261441638435034683,2895359499653916469,-7128503708641009368>()) {
                                    case 262069947:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2o77kndh4jkju","l2URZC5NtOaCJnz76JkcaZfglqhP/V8sTc3DEvj+wWA=",-6741801513599654632,2057481122111108652,-1805791857670459873,-2195851664784158708>()) {
                                 case -1405443313:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (!ab(var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s32vsm6ag49qkr","mAmB5P5YcZdtaCE6U3md3V260jFbH1/L+lOQRipFerw=",-8496470124703163833,8892005629536425988,6286069525636719845,4446906522736760217>()) {
                           case 1408579271:
                              return;
                           default:
                              throw null;
                        }
                     }

                     a(
                        var0,
                        var3,
                        (String)com.yiyiaddon.m.b.a<"s2f64lpxitbcrt","AxNvxsxpLGgwL10gaIwWYVXzDx9ST/CPv1oyuPyyhDFr2qpphAOOTSDN",-8238246297877986898,154100361840396231,7365035631892470480,-545570774304747933>(),
                        1
                     );
                     return;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private static boolean ab(String var0) {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2u9r6c3gnt4d6","VYIWlWQ5m3pnTjZggE4tA3da4PYwyURz0dCqXb5h+1Y=",-5431426390102541287,-2740754272447094983,-6873685855976991264,4393443190705384068>()) {
         case 1855100575:
            while (var1 < var0.length()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ecwa46yzphck","7nthBtzzU0RWvxBtM2WNVk4Qi6TNjgpL8cB6F6xtIZY=",-7776531999015845105,-1485054272171394743,-498728376881884341,6745729132752177262>()) {
                  case -1324737165:
                     if (var0.charAt(var1) > 127) {
                        switch ((int)com.yiyiaddon.m.b.a<"s178q0z4lx2rqd","cs/EIdbmOVBAXRCvD7YQCFZtJNyen7zOsMofGB3Eekw=",2502340974723113068,-5000923996797162490,-2927463060861453086,1896803264275630311>()) {
                           case 1349843553:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var1++;
                     switch ((int)com.yiyiaddon.m.b.a<"stiz36d5v2wtm","r+SOHn7UOGtXDmb69Vku2f3u14faf/iffmp2Xe7A4iI=",-1133205204503062099,5629866610659811249,2886808097319963787,-8328679070877624826>()) {
                        case -173356783:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var0.indexOf(32) >= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s19bkrxx4bb6j8","rByOeVsAFc7jJE3O2ZRMrM+8UA7ibNktqnwgMz8YBpo=",-150085632288908370,1928221534901296146,-4632285716181391510,1739575652408789261>()) {
                  case -713511162:
                     switch ((int)com.yiyiaddon.m.b.a<"sxr1re5z1sgbc","GJrTDnVwkbGsYTg3+n+zAh33/XdWRxH1W2KQ56OlDtA=",-7311072093079605432,8401150490944056758,-6383928901136337625,-3372388698360639297>()) {
                        case 705546083:
                           return true;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s157eky55kh09v","moS/rdYo4hyEYdkQ4hJr3BGJF9tjTfpzCB/s0dSihtE=",3138478741882524217,2736836042439268786,-2827466213158203571,-3939664389362787911>()) {
                  case -234183739:
                     return false;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   private static String a(Collection<String> var0) {
      String var1 = null;
      Iterator var2 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3anwfv43v9tqg","OGX5qPls927MrM9HzYIaNIlCeFFHNjTwjJIuEoc+b4k=",-4237351460863424591,-2696803751661403903,8137194218896076549,3935435205697178845>()) {
         case -120834875:
            label91:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2kyyn65wli46f","IS+2/SHvqtMSB9GQyA1rf3DgqFpYLa4l7x/5oJkFCn4=",6192216125129327751,8639808887320091334,8895344873705021417,-4034460864779532849>()) {
                  case -183614971:
                     String var3 = (String)var2.next();
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2fuw84t3qdgrz","tho4VF2EwVvtxy3a9lflUeO4JwdFiKJC7siobrcFzrI=",-7302382058131008665,3865427697529913117,1352276353832955530,-4288331906223971609>()) {
                           case -1123033846:
                              return (String)com.yiyiaddon.m.b.a<"s2nwbx65woocqn","DpFEpXpX+8sbdS79zjcFQQic/PQ2oGNtY2QTkA==",-1358538342858351694,-7088273900209591025,-8515645486861810610,-7015136277510476593>();
                           default:
                              throw null;
                        }
                     }

                     if (var1 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sux4zvrsht3tq","Z4aU3NJy/BFjnuvDESLoy49x44oSB/SApM3swsgAHHI=",5136139691524096066,-6594017672726494803,7474263875460385665,-3045911538885111459>()) {
                           case -12722801:
                              var1 = var3;
                              switch ((int)com.yiyiaddon.m.b.a<"s3v2wlx6czrvqq","8c+A62iHNDu6jy9C4yoc9IliQuBOk4/eS9TxXwOdzgs=",1943274351179225761,-512560717669765948,5770436190725384952,1072712892169591468>()) {
                                 case -1722398773:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        int var4 = Math.min(var1.length(), var3.length());
                        int var5 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"svhgzc1jeyxql","5AJYPuqfwE/gNjUCH1MDoUHhfLzProRAcT0MPpannPo=",-538675804329416852,-6924597131074582952,8225181176280468170,1774827853433619173>()) {
                           case -1307199691:
                              while (true) {
                                 if (var5 < var4) {
                                    switch ((int)com.yiyiaddon.m.b.a<"ssqalc5gshhpk","fydAZR9LnVw/48RSRCf36hvqScFA8FQs875VQQ5e2fg=",-4581767379736938435,-5825662141855797329,15166906936784883,-7311974323348260212>()) {
                                       case 468731265:
                                          if (var1.charAt(var5) == var3.charAt(var5)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3j9yf8k5y5hw0","cAZ7Cf7t+8Bk7Y0AmMutErgtLZLWBr5mcvv4wQWBh4k=",141286740503229461,8958404817108219704,806025120304052850,-5793716397759143103>()) {
                                                case 965628901:
                                                   var5++;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3liscof1a2pmp","pvauKaur/mW+wDRqKcGNGhQboCQYTMGknoH/9TDtM/M=",-5258037153365860229,5564490873348336445,3480223924849524130,5974071471890999922>()) {
                                                      case -2052843312:
                                                         continue;
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

                                 var1 = var1.substring(0, var5);
                                 if (var1.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ji0qwdkn48tu","Jgi00+1YRnLGu8nnEuoEvuk8OqhAqQTzRV1X9l5rR4E=",5342205188168601915,63475592762067060,-5389754280244250916,9037851082091760600>()) {
                                       case -1888338440:
                                          return (String)com.yiyiaddon.m.b.a<"s2nwbx65woocqn","DpFEpXpX+8sbdS79zjcFQQic/PQ2oGNtY2QTkA==",-1358538342858351694,-7088273900209591025,-8515645486861810610,-7015136277510476593>();
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1ac9mq2b2wb7w","dksTwwFAwJd3FvnOQyrKr/8Zork6cZ+JIRl4akAi4t4=",8608313912208540821,4315356417949786395,5465096741201204292,-4543811633858662259>()) {
                                    case -1924060365:
                                       continue label91;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            if (var1 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3tvyv8ytt1qbm","KqhqgvvU5KEtZDHKbjl6aL/5ksm8geXm1L//M17SCok=",7293122647999334120,6609986717311890890,-2877789280707813487,247489762586623195>()) {
                  case 2031370918:
                     String var10000 = (String)com.yiyiaddon.m.b.a<"s2nwbx65woocqn","DpFEpXpX+8sbdS79zjcFQQic/PQ2oGNtY2QTkA==",-1358538342858351694,-7088273900209591025,-8515645486861810610,-7015136277510476593>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2d2ygvbkgy7qi","fMOnDJ3kCZL5NGi7Gpwf8Uhemhkf6hiOYijXEPa/o78=",1849595131201882394,-5829044003407190196,-1791765666848443066,4242263314273194381>()) {
                        case -364497451:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s113ssa0gh43ku","PAfVz2Se2fjQwUZSZke/ulEYTj7cDMNrqpnANzQe8dY=",1527950802816367166,5172247170750364844,-7275794038604491451,-9041276998870815506>()) {
                  case 1646461303:
                     return var1;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   private static String aG(String var0) {
      String var1 = var0.trim();
      if (var1.endsWith(
         (String)com.yiyiaddon.m.b.a<"schlpd7g70yyf","2xfYHI70DbKCqjQp6g1oEfa+YqpDuvqtJ6BKTbwf9Pg=",4985421542861487695,-1369927674547792636,-2290150350646594389,-8033432617257476878>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s100923cuethsu","9KbTu2yLlH5tXwSbPRHuSAH8L+ioP4BRCQwOutHdY/g=",-4633582872241350625,1554646065999350221,8447092841912345414,-714083886418650179>()) {
            case 1834744121:
               return aH(var1.substring(0, var1.length() - 2));
            default:
               throw null;
         }
      } else {
         String var2 = var1.toLowerCase(Locale.ROOT);
         if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s2hfxqwwkjaguq","0xNWhHfWsUL7fsGp1kZzAWpYQcduSiCiaBHvGXJ4d3I1mIbMeXlNcQ==",6993548588871357329,-3643763616997273415,699828930797158129,-3378032543682924370>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s36ep73pv5gcdk","EPF9lZMGmcEmm6cttJ7r1/1mC9Tl/ZO7Fn3ZcVrZyo0=",9188767605757964834,7496183898132429752,-3862840424927886482,-4607435334541025491>()) {
               case -245059148:
                  return aH(var1.substring(0, var1.length() - 6));
               default:
                  throw null;
            }
         } else if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s2d6086w378gx6","iO6haWWQyX0BG+GbxI9QO1Bg6JX0lqNVKkDrJV2X2peL0yEgmWc=",-7890213891793462413,-8043079276972781872,-2785403530579332301,1778281117886884980>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"ss7xq29cc1ysn","h+NPRykkLb/c/uKNzRV3p+qywpczJG8Jox/JIPbnU5o=",6167116564445823429,5190624053721797155,438257312339879838,-483277824699328246>()) {
               case 664832776:
                  return aH(var1.substring(0, var1.length() - 5));
               default:
                  throw null;
            }
         } else if (var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s17pv9vx2caxvf","crsDL52/2IzBNFXxCeO2nMzoygRNoQllIAWkfsPb8lXNB7p1AmuEGA==",-7607651844402622289,7490287945741543436,3087683511863834116,-5815673191309480244>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s21d7er8if0s1q","Ppg2fM0CgNcxQRvnunf76sdqaN9IjhHO/mIYJLcfCF0=",-7834010520465951448,5736835887871278060,5597414227988121252,-8219285785108270800>()) {
               case -91280911:
                  return aH(var1.substring(0, var1.length() - 6));
               default:
                  throw null;
            }
         } else {
            return null;
         }
      }
   }

   private static void a(String var0, String var1, String var2, int var3) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3auahzv8pkv7n","LhqlGWmoGFBnUYofcTzIaGAywoF9GEemdWwhf6qcUZA=",2119642468109535856,-5959738526616231613,43366870918239432,-4977286872518423846>()) {
            case -1810513632:
               if (!var0.isBlank()) {
                  String var4 = aj(var1);
                  if (var4.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s36h99ysb9rg5j","KPLxnbee4XyGkVRwLtPN9bqqk3JVjb5WuhQyDHRbe7o=",2432301298359622575,1205644551229810621,-8460579754723171945,150200121090495325>()) {
                        case -1677471119:
                           return;
                        default:
                           throw null;
                     }
                  } else if (var4.equalsIgnoreCase(var0)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1iyvoi2w2q82m","7LqK7JgZ6ILysinRD8aTlMCDrfwHlhW2JFqobK3H+74=",-5601890812148024775,4716626638600382541,7310818338907001260,-8577639197532463025>()) {
                        case -1189494766:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     String var5 = U.get(var0);
                     if (var4.equals(var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s18ek0xrmoc23u","OWWU0J1VasBZH8BdyEAIRtKnU1S5SAfQEnQz1PxNvLo=",-7439101431871601785,8427730370747192262,9091558150404240498,-4884029911060627941>()) {
                           case 978600877:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        Integer var6 = V.get(var0);
                        if (var6 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"snued6s1nzed8","94mytX7IKlK83bF4ox6XidPaNWOV2hSkCxu3HQPcdhs=",6646048979761430504,1473086678224220462,-2165935263498299862,6362807564391814857>()) {
                              case 198299397:
                                 if (var6 > var3) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1sxpzhbdmyolj","IYLfEoSovSWcx3Gbiy/l1vR+XSd9b3ai0Q8YsYAxVfQ=",-6969093069233076464,-8132936496828686435,-8079342501132364130,-952497509213591542>()) {
                                       case 1495559958:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        if (var6 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s176qbwz5n4unl","vmZzLetlJBMhzC/r998IWCzSSNOqez5pu4otGIjHdbY=",-3352780220241588791,-6824353497830208570,-2504818553288746744,-3171058708799183649>()) {
                              case 280049753:
                                 if (var6 == var3) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ylpwxa8xtdkg","srQvGb8h52v8hlk++CH5OLI/QIoTNqA/uLsOUFilVZg=",2530938269937492763,2530981508644311527,-3700346295368103117,1921975671668776804>()) {
                                       case 1481106577:
                                          if (var5 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3me20j3z6wx81","l2JRVwCQFkApcuWhdjx+nZCw0LhUlRdUVZY4QcY50ag=",7581492751024742043,1230070844982490020,-2963597634729549176,-2588800201511099759>()) {
                                                case 1183586530:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        U.put(var0, var4);
                        V.put(var0, var3);
                        W.put(var0, var2);
                        bx = true;
                        dH = true;
                        L.add(var0 + var4);
                        return;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"so5ww0v24wt70","p1Vt9yWmhHXp6VXlfoTH4wvgiMEgdNZtYRNRBVhaVIc=",97121625143167885,4618227292566386387,-2864709813756995494,5134258442902513287>()) {
                     case -1828576132:
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

   private static void cz() {
      String var0 = com.yiyiaddon.k.e.c.bT();
      String var1 = com.yiyiaddon.k.e.c.dn();
      if (Objects.equals(var0, sH)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1t9n71zhqki28","MHrHiTnnWN5Fg3BgSwSQIBjw9JAHIZvKC1GBl4GXjlg=",774671876259124409,5198254617516992397,2986178884151616733,-6410345973958004066>()) {
            case 1245246163:
               if (Objects.equals(var1, sI)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ojy40i8r1iul","CSxLxslLLhRqkgSfZRwmbSIhAi9sXiqe64xMftRWMPg=",3658494088268078925,-4521428896780024399,-9047230813011991958,-7184751332983699914>()) {
                     case -98182571:
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vgq1onnqggfs","fmeIFPmy8gbyYRXYC897QFYQfW0VixYpQX8JqiQhv3U=",74222000964403105,-4355471838646368455,478613099354859828,-3683211872879311336>()) {
            case 1192458180:
               if (!var0.isBlank()) {
                  boolean var2 = var0.equals(sH);
                  if (!var2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2axmbgiuliq4y","0AiFmqkhEUHW/x3I9mC+ENRKydehbG2NJmME+eHo4+I=",5469130133341294274,5221887306334978451,-7716461141877434004,-6723711432617426096>()) {
                        case 511759815:
                           if (dH) {
                              label55:
                              switch ((int)com.yiyiaddon.m.b.a<"s79nxob8spcx8","MqpGVP1QMKKDbaFHY4MA97nvf6w/xrU1AlnKJc1MybA=",-8067828721999546550,2366047295326815982,-8901055777347886452,-1172651448541179458>()) {
                                 case -1234384937:
                                    dH = false;
                                    e();
                                    switch ((int)com.yiyiaddon.m.b.a<"slaz0r1spqjgu","3UPiLtpxT6MPzSduA6RqHqQRlE+15ds85bomxduoAi8=",3884528396989156048,4137658664055668128,5250830349289663979,7077889079838077562>()) {
                                       case -983958777:
                                          break label55;
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

                  sH = var0;
                  sI = var1;
                  if (!var2) {
                     label50:
                     switch ((int)com.yiyiaddon.m.b.a<"s2l8pzhxk42mdm","6j11pLCzzadF867DBKMp3zCgWVjJ7WMIkoVeiE09XRg=",-1596125556317514681,-2420178824688753265,-1889196349276664517,-5017625334695255598>()) {
                        case 734276058:
                           U.clear();
                           V.clear();
                           W.clear();
                           X.clear();
                           bx = false;
                           switch ((int)com.yiyiaddon.m.b.a<"sm5ogdmugan8y","jkmFQONL1OpaUM7FIljo8kXbaeYIlOz57K3QuBnVJCg=",6161744047431251286,4216874811087209133,8408641433374518205,2600241563623970846>()) {
                              case -630582550:
                                 break label50;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1vidzzteqox03","CLiAAuYy6PVaUGWImlMzFtS5V3hxrH8FxVWdQZoC8Wg=",-1576391616939585636,1874345462810807335,2317281224819434498,7607297782911699614>()) {
                        case 1152396470:
                           if (!var1.isBlank()) {
                              d();
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s502eqscqk1dv","EcSKvrCiHWdkdfqJBtOT6lywWS3LeUAlKMvfjHYqHSI=",3674569978604572217,-384310094898784528,4472699310944041586,-2595394742676677482>()) {
                              case -442835702:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1pggsp5ar9wkp","fZoKODGfDiegLAzOLZX91sD/ksYdg2zOMHdPLNvggcQ=",-9055274694284592287,1036583682545054437,6630098783494270131,-2981188193779138614>()) {
                     case -1318892196:
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

   private static Path c() {
      String var0 = sH;
      String var1 = sI;
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqwe6gomeljfv","KLE/XTg6K7CGOB2mgjULGWoOI/6jbc5ThUp1b4WrphM=",-3521361386751518031,5365373237058030597,3428822085050861339,2570913265225369259>()) {
            case -2062751290:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"saum1tbnw0fil","xk7qfU2KyuhYsk9NNJ+mQnkH/l/jjkkFa4tgI/KECsI=",-6808091976288216096,-1612349029977616256,8284008906390191816,-8985494481814755917>()) {
                     case -1568918825:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sfzxu59iv2b9a","EXQU+j5qnVqfIK4YM3/ZkpCxjv2QKe1yVNplTW5JHYE=",-7001432789466102106,-4003957129717862687,-8193463488850765434,1268803294713414011>()) {
                              case -1516243647:
                                 if (!var1.isBlank()) {
                                    return i.resolve(g(var0)).resolve(g(var1) + "");
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2jjefvwtlqex1","kP6CP1G8ZLc1Pr65fwJCYnUPEddjbzyEWjIAGZuWJnI=",-6546027217573754879,-7754104618182534169,-2306694506546851554,-2434546366149675827>()) {
                                    case -858170755:
                                       return null;
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
               break;
            default:
               throw null;
         }
      }

      return null;
   }

   private static void d() {
      Path var0 = c();
      if (var0 != null && Files.isRegularFile(var0)) {
         try {
            JsonObject var1 = JsonParser.parseString(Files.readString(var0, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!Objects.equals(
                  sH,
                  c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"s37ke63wxs284m","wq9Is3Nw1tXEFB4HYvYJBfwie36kbboLCcPQWEH3+yFG4A==",-7556255211449984656,-825300122159862287,-6988116251619254656,2903865452545727033>()
                  )
               )
               || !Objects.equals(
                  sI,
                  c(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"s29wxbv0ok0x3a","ywHMo6M50V3zZp1gIYNYo8G+vYXgxQAQ9MO+SkD6PwKsIFmU",4331942106540966006,7892488395956000531,4466805997204790046,1783138208476320381>()
                  )
               )) {
               return;
            }

            if (!var1.has(
                  (String)com.yiyiaddon.m.b.a<"sw1r5bw84dlkw","Q+Z5SO1d1bKfjzeT9C225qT/Vgq0BwZvkbpXP/WyAUcEF45X",952494265692130880,-4578912360929732841,4402314529382146067,3369449066043580032>()
               )
               || !var1.get(
                     (String)com.yiyiaddon.m.b.a<"sw1r5bw84dlkw","Q+Z5SO1d1bKfjzeT9C225qT/Vgq0BwZvkbpXP/WyAUcEF45X",952494265692130880,-4578912360929732841,4402314529382146067,3369449066043580032>()
                  )
                  .isJsonObject()) {
               return;
            }

            for (Entry var3 : var1.getAsJsonObject(
                  (String)com.yiyiaddon.m.b.a<"sw1r5bw84dlkw","Q+Z5SO1d1bKfjzeT9C225qT/Vgq0BwZvkbpXP/WyAUcEF45X",952494265692130880,-4578912360929732841,4402314529382146067,3369449066043580032>()
               )
               .entrySet()) {
               if (((JsonElement)var3.getValue()).isJsonObject()) {
                  JsonObject var4 = ((JsonElement)var3.getValue()).getAsJsonObject();
                  String var5 = c(
                     var4,
                     (String)com.yiyiaddon.m.b.a<"s3gyidfbzajx89","gAdOtHsb+ZTHOCmjVcEj/HQzznxfD6hzu7+za6fKBzI=",-1965732431467321908,-7010299180349796619,-8634763420619666495,3912408651227375957>()
                  );
                  if (var5 != null && !U.containsKey(var3.getKey())) {
                     U.put((String)var3.getKey(), var5);
                     V.put(
                        (String)var3.getKey(),
                        b(
                           var4,
                           (String)com.yiyiaddon.m.b.a<"s1gpyicr9eecn5","XZlMUZXIXaVtuOsG+34KGBi6FL38D/kjubkOeRwfAzu4IA==",-5891645713566281989,-4256576399095263848,3931857086809843163,-8056243054948860730>()
                        )
                     );
                     W.put(
                        (String)var3.getKey(),
                        c(
                           var4,
                           (String)com.yiyiaddon.m.b.a<"s3bdodeqkx3j15","9GEUVi4/y2bEn23lF87zOOa+s4jgpmzE6wUdH+3v/tc=",-4040768598751967729,-5533152885444781005,-4152434438667049084,-6199990761248842853>()
                        )
                     );
                  }
               }
            }
         } catch (Exception var6) {
         }
      }
   }

   private static void e() {
      Path var0 = c();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sz7r1tm8ttxyg","U1Gh5n9Cj6G+BipbPh/guIfWSGdTqeQ5q/g64Yxx35o=",7269889478656711916,-6424285704596329608,1776044964871925966,7320818107847544974>()) {
            case -1572989065:
               return;
            default:
               throw null;
         }
      } else {
         JsonObject var1 = new JsonObject();
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s37ke63wxs284m","wq9Is3Nw1tXEFB4HYvYJBfwie36kbboLCcPQWEH3+yFG4A==",-7556255211449984656,-825300122159862287,-6988116251619254656,2903865452545727033>(),
            sH
         );
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s29wxbv0ok0x3a","ywHMo6M50V3zZp1gIYNYo8G+vYXgxQAQ9MO+SkD6PwKsIFmU",4331942106540966006,7892488395956000531,4466805997204790046,1783138208476320381>(),
            sI
         );
         var1.addProperty(
            (String)com.yiyiaddon.m.b.a<"s2ofuj2hzdlykx","W2TiI53HLafOBCtaMfxNEsya8CIfBJVw/kCtauu0svE=",8852027359370716296,721745530270993988,-4341140345046897313,6010865800346307888>(),
            1
         );
         JsonObject var2 = new JsonObject();
         Iterator var3 = U.entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3r2ku19dv23fz","TqVEPhuAwwwP8bzmnKnYmD0siBTCpa9qiVVJ6JN99G8=",1863103784435143586,-2072124734575737745,4351875675611530193,-2336495439071308044>()) {
            case 242719400:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2oeiumq8bd26d","sIexQ4PU7Xjcao/YlV/MvbtTiZmuJ32MC8oZzUaNQhc=",-1688807751890710133,697980325831829406,-133272811943475247,6436923107683176683>()) {
                     case -1684670811:
                        Entry var4 = (Entry)var3.next();
                        JsonObject var5 = new JsonObject();
                        var5.addProperty(
                           (String)com.yiyiaddon.m.b.a<"s3gyidfbzajx89","gAdOtHsb+ZTHOCmjVcEj/HQzznxfD6hzu7+za6fKBzI=",-1965732431467321908,-7010299180349796619,-8634763420619666495,3912408651227375957>(),
                           (String)var4.getValue()
                        );
                        var5.addProperty(
                           (String)com.yiyiaddon.m.b.a<"s3bdodeqkx3j15","9GEUVi4/y2bEn23lF87zOOa+s4jgpmzE6wUdH+3v/tc=",-4040768598751967729,-5533152885444781005,-4152434438667049084,-6199990761248842853>(),
                           W.get(var4.getKey())
                        );
                        Integer var6 = V.get(var4.getKey());
                        String var10001 = (String)com.yiyiaddon.m.b.a<"s1gpyicr9eecn5","XZlMUZXIXaVtuOsG+34KGBi6FL38D/kjubkOeRwfAzu4IA==",-5891645713566281989,-4256576399095263848,3931857086809843163,-8056243054948860730>();
                        int var10002;
                        if (var6 == null) {
                           label32:
                           switch ((int)com.yiyiaddon.m.b.a<"s3c1wjz02r4o40","xr1Rs1FNaUOyeiQl41F6WQEo6hixLWGEXoeZ7PKnkXQ=",-1538977390052903810,-319672004405929372,4955216001244692985,-2150765676680856812>()) {
                              case -2086541796:
                                 var10002 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s344x9sd2nm9tz","zqmzmfUJqScHnRCk1okRNZ5yVjFc1i7dhkvDBfQ2DSE=",-7155615110669663204,-6077947996660746642,5283006579594833450,-202543748725606215>()) {
                                    case 223423223:
                                       break label32;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10002 = var6;
                           switch ((int)com.yiyiaddon.m.b.a<"s27bv4deqgpvwe","F120pRs0gWWHl+Qnt5uu/08dXDFDibk2EKI0EMbvEQQ=",-3063211053320406331,8900443589177782900,-353902128342993599,-7886370470932491780>()) {
                              case -129059093:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var5.addProperty(var10001, var10002);
                        var2.add((String)var4.getKey(), var5);
                        switch ((int)com.yiyiaddon.m.b.a<"s2mfk6o44jo2is","p2Ecqk1Zi9T4PiLTzO3Tx+4GKQzB+9LZofXcE9uoP7o=",3029611522674919990,1383232922135199754,305438400301342779,7760143626384452879>()) {
                           case -1475328969:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var1.add(
                  (String)com.yiyiaddon.m.b.a<"sw1r5bw84dlkw","Q+Z5SO1d1bKfjzeT9C225qT/Vgq0BwZvkbpXP/WyAUcEF45X",952494265692130880,-4578912360929732841,4402314529382146067,3369449066043580032>(),
                  var2
               );
               com.yiyiaddon.j.a.a(var0, var1);
               return;
            default:
               throw null;
         }
      }
   }

   private static String g(String var0) {
      return var0.replaceAll(
         (String)com.yiyiaddon.m.b.a<"s1e95wsf8ecy6i","sdgQY7ZuBRemUB2/9iZW9V7C94RrPf8HtED9+JLsEJwXZ8w4kUett5I9NT9ha/ArPuEnzA==",9078368753789686996,-1951367789277966414,-2721660195471239628,2527506668142073324>(),
         (String)com.yiyiaddon.m.b.a<"s24ovqbc2ucepd","snproqTwN5V9cYeRVbeERVouaWc1n0zwbU/U/9bJ",7158687115095639378,-4754544706457769673,4376091622811655355,6218840977948502360>()
      );
   }

   private static String aj(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2etxzrvjkjp72","pdd9D+q3q62sskAT1KlCzuOT2r3N0rSRT3j88XguxjE=",5111793416908764046,1347735953817695488,3343162557031581309,9091712864121959585>()) {
            case 1749888261:
               return (String)com.yiyiaddon.m.b.a<"s2nwbx65woocqn","DpFEpXpX+8sbdS79zjcFQQic/PQ2oGNtY2QTkA==",-1358538342858351694,-7088273900209591025,-8515645486861810610,-7015136277510476593>();
            default:
               throw null;
         }
      } else {
         return var0.replaceAll(
               (String)com.yiyiaddon.m.b.a<"s2g8p1esszy1jk","greSnYPIVwrMG1eZVeha8SabVTekqEw8S7h29m0g4aweihrW9ifG78g9WRUDJORSvbxP4E+urTkjP5ZaaMEXZFmUAjCPDQ==",4544473460500701600,1403494338505652205,-9037333878480656496,4157815857127572015>(),
               (String)com.yiyiaddon.m.b.a<"s2nwbx65woocqn","DpFEpXpX+8sbdS79zjcFQQic/PQ2oGNtY2QTkA==",-1358538342858351694,-7088273900209591025,-8515645486861810610,-7015136277510476593>()
            )
            .trim();
      }
   }

   private static String aH(String var0) {
      String var10000;
      if (var0 == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"smcj2hz7w5pn0","suF3Mchk+9u1Nz0wIgLDvXUz6CN9j2fylC5lI5Qd6AU=",3396285826827771315,-5218720840086010680,4900818993891808599,8237653593054015990>()) {
            case -7125294:
               var10000 = (String)com.yiyiaddon.m.b.a<"s2nwbx65woocqn","DpFEpXpX+8sbdS79zjcFQQic/PQ2oGNtY2QTkA==",-1358538342858351694,-7088273900209591025,-8515645486861810610,-7015136277510476593>();
               switch ((int)com.yiyiaddon.m.b.a<"s3rb7zi6w759i1","qz5wqIzBYhiCep4RZYIr5/Nes7kzdgPF+F8h6OKpd3k=",394866244302347853,7547197696786732800,1611989326555258180,-887185925201319112>()) {
                  case -900734195:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.trim();
         switch ((int)com.yiyiaddon.m.b.a<"s1x6w509p0uays","AZHSby0HaSpM8OofyEOq+anV+JKxyvNMnrtoCraPOyo=",3150887108454514979,-4796612115349897426,-1073129665211077969,5713042151154421553>()) {
            case -228785414:
               break;
            default:
               throw null;
         }
      }

      String var1 = var10000;
      if (var1.isBlank()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fd4yf1p4ztra","vQxXGN2b33j4Kzjr0l9yQYx3PDfLeZlSJghSjNk38DA=",4482589118302629261,259265268943193228,2602019595373070845,-9119390533721796779>()) {
            case -179389154:
               switch ((int)com.yiyiaddon.m.b.a<"s323x93dq7c9wh","fK9FVAHqz67rBdQEQaHPRlyqvBehLrwWvj21xU5T7V4=",-8461434425047107242,-6717101373422389873,-70340275359624669,1273160755080185964>()) {
                  case 149591925:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s126lnxqw5k58n","Hfcy+jshuMtTUArXb1jY4CmZJYaZJja7yQEABWD1rI0=",-1178939646454602036,-7854196786720860702,-8784504826165222349,364730476962094996>()) {
            case -1891376518:
               return var1;
            default:
               throw null;
         }
      }
   }

   private static String c(JsonObject var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bisptz2slzgb","ucSfkbNqEvU3ayqFW5FNmOAry/xvpzhwFBK9cQJk3DA=",-5611835159200055751,-4945787654358394960,3914524129418550151,-1651106091125382570>()) {
            case 1776145542:
               if (var0.has(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2zz1irrx1u60e","TnDcmFCbTzooi3vVuZGRfxt1ATnjlvlBO8xqQnJD34Y=",-9018686141337130161,1441696391456248918,2595542498444386827,6347663031472563480>()) {
                     case 1942559142:
                        if (!var0.get(var1).isJsonNull()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2mycxjjinvii","gVqYS7I/QaVThflpFOT1GFqMCh0Mi0bf6XNRZy5uQcE=",5005821619968507254,-2738428271764430097,-95480573996468590,7505458538187842561>()) {
                              case -854866297:
                                 if (var0.get(var1).isJsonPrimitive()) {
                                    String var2 = var0.get(var1).getAsString();
                                    if (var2 != null) {
                                       label35:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1t7qvmtqmi24f","QKnWNhLTyhF1Uc4+i4+Rj1B0XKGWcM7H3b1bHTGLwqg=",5319704194186548084,4693295197545556139,115131175299322315,-7015052773603117509>()) {
                                          case 1376064280:
                                             if (!var2.isBlank()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s38ufksvnwxlbd","MV4jmygtql8PbV0a8BODhaLqfRmMKxd7M9P35QO+kXg=",-6896733559037115829,-1825081369665422234,-7082763298958199622,-6032822815391190545>()) {
                                                   case -1616394546:
                                                      return var2;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2a79jwfb7tzdz","dVW9AfInhq/o+j8yvBV9bc3KcFKYqE1VDXJpjXrBXj0=",-5475674541721272927,-5355189577234738182,-1478351560169208464,-8747870738321687251>()) {
                                                case 1868341688:
                                                   break label35;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3l2vgsc78ib45","VgIV14USEikkH+xpQu9pmPTIJGt5dEnH8oam7AkeaB0=",-2754700164968856508,994143190922702833,-7182991286983613487,-3064268861661929242>()) {
                                       case -1685673561:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s35k3pyi4y87i4","IyLtDFT1MEwEsks/jM9jZBAxNeNVSKm4IwgkSDdl4cU=",1264654648782512645,6380100537148652930,8668902874686052364,-7263939933073284579>()) {
                                    case 1242902275:
                                       return null;
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
               break;
            default:
               throw null;
         }
      }

      return null;
   }

   private static int b(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && !var0.get(var1).isJsonNull() && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsInt();
         } catch (Exception var3) {
            return 0;
         }
      } else {
         return 0;
      }
   }
}
