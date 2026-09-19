package com.yiyiaddon.e.f.c.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.c.e;
import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.h.g;
import com.yiyiaddon.l.j.k;
import com.yiyiaddon.l.j.l;
import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class c {
   private static final String iX = (String)com.yiyiaddon.m.b.a<"s23j6rywg8rqk4","d/F8ZHVsKct5q1MrNgKbcHaXZSBFtxTjPoW1M75Q9iAGuLmf",-8725021964134615453,-5113864469646138616,4104058012515289070,-4830590664972873591>();
   private static final String iY = (String)com.yiyiaddon.m.b.a<"srhsn7jgvmsx6","7kHCGUnLZB2sS8gAE+HzvwEC3k7ReEy/tTYuDLxUw9Vaa1HCUcoKbosN5oOSjPWYRUq79A==",-1484638887231867999,-8635866863576884765,915059224176818083,-4462254125853661193>();
   private static final String iZ = (String)com.yiyiaddon.m.b.a<"s3kd3cirit0gqy","yUzZahxt3W+yevhqz3e6IiKrkMimKyj5ubhuwCduhanVTyYLOSnYaE54vyg=",3697545775253548947,-4399031448475827226,8072339655762321437,2105153246730355901>();
   private static final String ja = (String)com.yiyiaddon.m.b.a<"s3751ukarc8gip","rlSbYmBpl5gb4lfU7s6H56rF31OCh+2xaoY1dEFAZ6e9CukU43UMWcAkEN4=",5253429920072393334,-8929868288803181357,-4579006123237392076,-7465955347706664596>();
   private static final String jb = (String)com.yiyiaddon.m.b.a<"s34q7kljmzkrpx","L4de1N6wRxLTHVf+hJG5LpMhBB+2ZVY7jBNhoOxvdzkD1KK2wmILplyD364=",6982618420845136701,8154989457569930834,-2416721191367839565,560735871542901765>();
   private static final String jc = (String)com.yiyiaddon.m.b.a<"s135xulzp6sgt0","jVZSYBibU7JCVjxpxS+OW7AF4otNymLEGVZixCZJX/CME7W0SNu/Z0eqQAyYW7pY",6175247208359982705,-5714295255731504575,2318334083857819396,4270151058678274706>();
   private static final String jd = (String)com.yiyiaddon.m.b.a<"s3mz5pv1mobcfr","aheJlTyv64oNRRObwQ9epFv/jfJD5DCUQaLecuG01hDrNyeWY6QM8UWjp5NO367M",9113651820524221117,-6421196814328719634,-440961856044544547,8671905076172857598>();
   private static final String je = (String)com.yiyiaddon.m.b.a<"s3nch7bjybr74b","sJ5nWhxNe9zQCUQi1eC0IguXterxcA81BeE6jwKgU2i0Z05rjmZ2R/TdPCw=",1180507474782769242,5226752020223152418,852331556095102365,8756274000232971277>();
   private static final List<String> ab = List.of(
      (String)com.yiyiaddon.m.b.a<"s3kd3cirit0gqy","yUzZahxt3W+yevhqz3e6IiKrkMimKyj5ubhuwCduhanVTyYLOSnYaE54vyg=",3697545775253548947,-4399031448475827226,8072339655762321437,2105153246730355901>(),
      (String)com.yiyiaddon.m.b.a<"s3751ukarc8gip","rlSbYmBpl5gb4lfU7s6H56rF31OCh+2xaoY1dEFAZ6e9CukU43UMWcAkEN4=",5253429920072393334,-8929868288803181357,-4579006123237392076,-7465955347706664596>(),
      (String)com.yiyiaddon.m.b.a<"s34q7kljmzkrpx","L4de1N6wRxLTHVf+hJG5LpMhBB+2ZVY7jBNhoOxvdzkD1KK2wmILplyD364=",6982618420845136701,8154989457569930834,-2416721191367839565,560735871542901765>(),
      (String)com.yiyiaddon.m.b.a<"s135xulzp6sgt0","jVZSYBibU7JCVjxpxS+OW7AF4otNymLEGVZixCZJX/CME7W0SNu/Z0eqQAyYW7pY",6175247208359982705,-5714295255731504575,2318334083857819396,4270151058678274706>(),
      (String)com.yiyiaddon.m.b.a<"s3mz5pv1mobcfr","aheJlTyv64oNRRObwQ9epFv/jfJD5DCUQaLecuG01hDrNyeWY6QM8UWjp5NO367M",9113651820524221117,-6421196814328719634,-440961856044544547,8671905076172857598>(),
      (String)com.yiyiaddon.m.b.a<"s3nch7bjybr74b","sJ5nWhxNe9zQCUQi1eC0IguXterxcA81BeE6jwKgU2i0Z05rjmZ2R/TdPCw=",1180507474782769242,5226752020223152418,852331556095102365,8756274000232971277>()
   );
   private static final Collator b = Collator.getInstance(Locale.CHINA);
   private static final List<String> ac = List.of(
      com.yiyiaddon.e.f.b.c.LOWEST_DISTANCE.m(),
      com.yiyiaddon.e.f.b.c.HIGHEST_DISTANCE.m(),
      com.yiyiaddon.e.f.b.c.LOWEST_HEALTH.m(),
      com.yiyiaddon.e.f.b.c.HIGHEST_HEALTH.m(),
      com.yiyiaddon.e.f.b.c.CLOSEST_ANGLE.m()
   );
   private static final List<String> ad = List.of(com.yiyiaddon.e.f.a.a.b.BABY.m(), com.yiyiaddon.e.f.a.a.b.ADULT.m(), com.yiyiaddon.e.f.a.a.b.BOTH.m());
   private static List<g.b> ae;
   private static final com.yiyiaddon.e.f.a.a d = new com.yiyiaddon.e.f.a.a();
   private final com.yiyiaddon.e.f.c.a e;
   private final com.yiyiaddon.e.f.a f;
   private final e c = new e(
      (String)com.yiyiaddon.m.b.a<"srhsn7jgvmsx6","7kHCGUnLZB2sS8gAE+HzvwEC3k7ReEy/tTYuDLxUw9Vaa1HCUcoKbosN5oOSjPWYRUq79A==",-1484638887231867999,-8635866863576884765,915059224176818083,-4462254125853661193>()
   );
   private static final Map<EntityType<?>, Item> w = Map.ofEntries(
      Map.entry(EntityType.GIANT, Items.ZOMBIE_SPAWN_EGG),
      Map.entry(EntityType.ILLUSIONER, Items.EVOKER_SPAWN_EGG),
      Map.entry(EntityType.PLAYER, Items.PLAYER_HEAD),
      Map.entry(EntityType.MANNEQUIN, Items.ARMOR_STAND),
      Map.entry(EntityType.LEASH_KNOT, Items.LEAD),
      Map.entry(EntityType.GLOW_ITEM_FRAME, Items.GLOW_INK_SAC),
      Map.entry(EntityType.EYE_OF_ENDER, Items.ENDER_EYE),
      Map.entry(EntityType.BREEZE_WIND_CHARGE, Items.WIND_CHARGE),
      Map.entry(EntityType.FIREBALL, Items.FIRE_CHARGE),
      Map.entry(EntityType.SMALL_FIREBALL, Items.FIRE_CHARGE),
      Map.entry(EntityType.DRAGON_FIREBALL, Items.DRAGON_BREATH),
      Map.entry(EntityType.WITHER_SKULL, Items.WITHER_SKELETON_SKULL),
      Map.entry(EntityType.SHULKER_BULLET, Items.SHULKER_SHELL),
      Map.entry(EntityType.EVOKER_FANGS, Items.EVOKER_SPAWN_EGG),
      Map.entry(EntityType.OMINOUS_ITEM_SPAWNER, Items.OMINOUS_BOTTLE)
   );

   public c(com.yiyiaddon.e.f.c.a var1, com.yiyiaddon.e.f.a var2) {
      this.e = var1;
      this.f = var2;
   }

   public void d(i var1) {
      com.yiyiaddon.e.f.a.a var2 = this.f.a();
      var1.a(
         this.a(
            (String)com.yiyiaddon.m.b.a<"s2b2yu31w204eo","fNDVlNWi/J2Vmdp8bJ6NBEBCVzgGIyMyVtuL7+vitVTgWlc2",-5669957250053892338,-4257036475878217502,1323425581117183631,-7446991948325530646>(),
            (String)com.yiyiaddon.m.b.a<"sbyjria67ro61","JtvtI9y6TuUPWcu0W84CJT0XJ8HXZ5E5HPpEqp3Ot4Mk5DIU9hKYp+lv/3LZOGfbih2Z2cJLRfE161avDTk=",-3710699130813770970,-5122236154349512226,-3999492236550141992,7067119790574444506>(),
            this::az,
            this::by,
            this::bz,
            () -> this.f.a().U.isEmpty()
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"spujx6w937tzg","GTKxv39BVUxe3mRWo9zetIOw/QoHRTDXWsSlf+3Psmtl7sCs+3g=",525332497766924896,563459281307987212,-2547176135557229010,-6045703016770603510>(),
            (String)com.yiyiaddon.m.b.a<"s15zpl0qhrr266","6oj9z5pfO+Hp7+GUfMfr1qenQpUmtoFSFBWCIJ5qygaWwkXm5NkJWEf6Om4zkA==",-3136055822788295979,-6135680022968520168,8149923752578921070,-8690184897917261161>(),
            null,
            List.of(
               new f.c(new k(ac, () -> var2.a.ordinal(), this::j)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"spujx6w937tzg","GTKxv39BVUxe3mRWo9zetIOw/QoHRTDXWsSlf+3Psmtl7sCs+3g=",525332497766924896,563459281307987212,-2547176135557229010,-6045703016770603510>(),
                  () -> var2.a = d.a
               )
            )
         )
      );
      if (!var2.aV) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s34n0kcsteh545","ht8Hx5Ud6ACjaFKKPsWC0KJFSBRvQ1CMlZk4Qd50Uus=",1733336232723975661,5904073267957219408,6296102683128797571,8318280459641277311>()) {
            case -1022475962:
               var1.a(
                  new f.b(
                     this.e,
                     () -> (String)com.yiyiaddon.m.b.a<"s1qa87l6fgs1c6","M0miZF0vEIkhXa29gs3QXXuKsWOnWXSXlyrQDftoLeSkaYCx",-909017323701645867,-8405549348685047992,2468581579636291156,4555523540443970076>(),
                     (String)com.yiyiaddon.m.b.a<"s12uzczayw60vt","a1ynEZrF104Bl6sjwv+do50ooZhvkBb9xjZSQbZ9L1N7JPU7qmfdYu4sTJInZZFr",-5792673601984645901,-3805927691516465965,-6642700759738476220,9023412611493142842>(),
                     null,
                     List.of(
                        new f.c(this.a(1, 5, () -> var2.dt, var1x -> var2.dt = var1x)),
                        this.a(
                           (String)com.yiyiaddon.m.b.a<"s1qa87l6fgs1c6","M0miZF0vEIkhXa29gs3QXXuKsWOnWXSXlyrQDftoLeSkaYCx",-909017323701645867,-8405549348685047992,2468581579636291156,4555523540443970076>(),
                           () -> var2.dt = d.dt
                        )
                     )
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2onbmobfavrwz","Mz3nGDFDbnxsw5exxY3fp91VTr96diLEyHI7Fj3b0/A=",1145376293512583039,794860597786374541,2146645845183498707,-1315405157309123616>()) {
                  case 1557096500:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s1os2gkzozharm","L1eUxAH8LLcxoaSO7k4jJZE5GIFTLYFHWHDfVfWUp+oJERn3",7007900179715858963,4038079804978062307,3477878096504048861,4288667817668685663>(),
            (String)com.yiyiaddon.m.b.a<"s1g4aho5ooz2nv","G78Clbr0Bf8YiXA1plU2Iaokc8esWjpfkKKsi3M2oQp2EK72bIFqGlkfFcm2UHconBJiAA==",-5260308276304994919,-4511069622998501672,-1569132704409419006,5830204529861639893>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.t, var1x -> var2.t = var1x)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s1os2gkzozharm","L1eUxAH8LLcxoaSO7k4jJZE5GIFTLYFHWHDfVfWUp+oJERn3",7007900179715858963,4038079804978062307,3477878096504048861,4288667817668685663>(),
                  () -> var2.t = d.t
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s2v20fj0p23fxg","AX9vghiXsgbrXw+s21EchRXeK0x+udaHDL8AkA+XHFx0ds2i",2744850050623814582,2109928682221824251,3809463317165438408,2614040714766344398>(),
            (String)com.yiyiaddon.m.b.a<"s2g1bj4aw5olnx","Og8ZMZpjLWsXet41VglY3SLrZqaDyz0Re2kg8YXQ6gegR8Wo/yySlhAQFxbc1JYjJuyvmtqtNIM=",-3356361049786874146,999693259226683161,8363030084551160426,2438539555677330804>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.u, var1x -> var2.u = var1x)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2v20fj0p23fxg","AX9vghiXsgbrXw+s21EchRXeK0x+udaHDL8AkA+XHFx0ds2i",2744850050623814582,2109928682221824251,3809463317165438408,2614040714766344398>(),
                  () -> var2.u = d.u
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s3t8dkqladrejp","NbHUzhk4ghu0+zK2nuNMUqkp+xqM84gJIo5Z6VCabRdR63M04gKjJQ==",1637031183464780826,-6076435212402805754,-203682363199290187,7179766230782538531>(),
            (String)com.yiyiaddon.m.b.a<"stcktd9nbckvy","O61CxbwfVRXbYzpfIZvaAPny4iLpUfPsN3imG1MU9r1J/KTAqIPCLDqUgoUczJy15Y0QPSkbKqiqlv2q8uFPvl33l+I=",587408567501034620,-6726465166297723403,-5774953756116916806,-2481612372109931597>(),
            null,
            List.of(
               new f.c(new k(ad, () -> var2.a.ordinal(), this::k)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s3t8dkqladrejp","NbHUzhk4ghu0+zK2nuNMUqkp+xqM84gJIo5Z6VCabRdR63M04gKjJQ==",1637031183464780826,-6076435212402805754,-203682363199290187,7179766230782538531>(),
                  () -> var2.a = d.a
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s30toubvq047ro","UJWDFl897H7xj+Co9wpWISnb4uPdhZaG3kOsupvso6J+xsmSvY09Bg==",-3377840400385281639,-7800537332417314512,-1945443844982092089,-9139661119847585385>(),
            (String)com.yiyiaddon.m.b.a<"sfibgnop6izx8","s6z5H/z27/da6+jUZEdncfaPOIml8RfcV9duFOVd/shEJ5DCreDEA1Wdh/Y8Gy+M60yUCOsGy8vnMLK8vxxEIUVMIhcO6nFfwvzKO7gr3IsLrj5q4LBFxA==",-1126795886818291831,1035161789976532170,-4400249651117667077,-8725496537100843577>(),
            null,
            List.of(
               new f.c(new k(ad, () -> var2.b.ordinal(), this::l)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s30toubvq047ro","UJWDFl897H7xj+Co9wpWISnb4uPdhZaG3kOsupvso6J+xsmSvY09Bg==",-3377840400385281639,-7800537332417314512,-1945443844982092089,-9139661119847585385>(),
                  () -> var2.b = d.b
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s9tsy9ennfo7a","jaClkBLRqjuvuK/dvZp35TK0zeIt9q7fVcBtmPq+GqE8TAHM2VJ2isqs",8490684572432367095,3720884152035745140,-3886874357455260551,-8406160220825835934>(),
            (String)com.yiyiaddon.m.b.a<"s3lmdur1jjqupo","XgwmU1m1HLCi+4IebES91XSXssUE/yKOM+AB587ACK98+crRKgi09x48SKkh7tFa9aY=",7872673273923586922,-8403752957166360612,2882578175400314702,5782688407069072097>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aX, var1x -> var2.aX = var1x)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s9tsy9ennfo7a","jaClkBLRqjuvuK/dvZp35TK0zeIt9q7fVcBtmPq+GqE8TAHM2VJ2isqs",8490684572432367095,3720884152035745140,-3886874357455260551,-8406160220825835934>(),
                  () -> var2.aX = d.aX
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s2gh7jutu05smb","HenncA8ZHycyXgNyMQwMVkmHeqfu4/k5LqBU4nvKK1NTxF94szaLkARe",3752199331390408867,4537414078349080611,-7567861145863592541,-5285408388531248379>(),
            (String)com.yiyiaddon.m.b.a<"s1t29ilpjyoflo","4809N8jsC+rM+OvuEPLkaZYv5ZalhJxJNT67a+jl3IOCbcSiFt5RnhLriBOS2zCr3LKrX8jHHvU7AA==",-6120274869390308588,-2895322715288078449,-5808134029408265971,2472035695057241155>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aY, var1x -> var2.aY = var1x)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2gh7jutu05smb","HenncA8ZHycyXgNyMQwMVkmHeqfu4/k5LqBU4nvKK1NTxF94szaLkARe",3752199331390408867,4537414078349080611,-7567861145863592541,-5285408388531248379>(),
                  () -> var2.aY = d.aY
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s2o1cvg14y9gn8","NT53MaXpGF0NQpxNf+qRuzKZVHjaYB5PTRTocms+n3Z+KXebrJ6qDyvhZBs=",-5668422669618809028,2478753407591167941,702127756872731451,6789294038691956445>(),
            (String)com.yiyiaddon.m.b.a<"s4wwiw4laocb5","K8NrpOvJJJVu5Qgmi8dcmP9E0ehoXbFnim2jHAWVEij/iUoBRUIkxntk5LfBdJXo",6539725698074128885,2608160164290402612,2949954266738491819,1918112375313460152>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.aZ, var1x -> var2.aZ = var1x)),
               this.a(
                  (String)com.yiyiaddon.m.b.a<"s2o1cvg14y9gn8","NT53MaXpGF0NQpxNf+qRuzKZVHjaYB5PTRTocms+n3Z+KXebrJ6qDyvhZBs=",-5668422669618809028,2478753407591167941,702127756872731451,6789294038691956445>(),
                  () -> var2.aZ = d.aZ
               )
            )
         )
      );
   }

   private f.c a(String var1, Runnable var2) {
      return com.yiyiaddon.l.c.f.b(() -> {
         var2.run();
         this.I();
         this.e.C();
      }, var1);
   }

   private com.yiyiaddon.l.b.g a(String var1, String var2, Supplier<String> var3, Runnable var4, Runnable var5, Supplier<Boolean> var6) {
      return new f.b(
         this.e,
         () -> var1,
         var2,
         null,
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s23j6rywg8rqk4","d/F8ZHVsKct5q1MrNgKbcHaXZSBFtxTjPoW1M75Q9iAGuLmf",-8725021964134615453,-5113864469646138616,4104058012515289070,-4830590664972873591>(),
                  var4
               )
            ),
            new f.c(new l(var3, () -> this.c.a(var3)).a()),
            new f.c(
               new com.yiyiaddon.l.j.b(
                     (String)com.yiyiaddon.m.b.a<"sjcg0ygeke98q","3VBz2wyYxpaNuw7o8Xs5fa7Uqh2YWWbG5b91vAL0",6440233484133841696,-5550658313664107735,-9022212472894627938,-966380281635994260>(),
                     var5
                  )
                  .a(var6),
               var1 + ""
            )
         )
      );
   }

   private n a(Supplier<Boolean> var1, Consumer<Boolean> var2) {
      return new n(var1, var2x -> {
         var2.accept(var2x);
         this.I();
      });
   }

   private com.yiyiaddon.l.j.i a(int var1, int var2, Supplier<Integer> var3, IntConsumer var4) {
      return new com.yiyiaddon.l.j.i(
         var1,
         var2,
         1.0,
         (String)com.yiyiaddon.m.b.a<"sfrdcs8ehc2tw","jL4gs5tC195ENuOeBHUSvy1nxbtCjpjtspZPDCfCSHZWPpxC",1568886609105497840,-7910655193424481398,-5479111081620764786,1840478417335837278>(),
         () -> (double)((Integer)var3.get()).intValue(),
         var2x -> {
            var4.accept((int)Math.round(var2x));
            this.I();
         }
      );
   }

   private com.yiyiaddon.l.j.i a(Supplier<Double> var1, Consumer<Double> var2) {
      return new com.yiyiaddon.l.j.i(
         0.0,
         6.0,
         0.1,
         (String)com.yiyiaddon.m.b.a<"s1357jl20zst7i","vixjnOLbIajpO3fxJK4s/S/sLwfbrXyBMy3akULyJVkDYJ6u",-7619788698054776712,-8161482118181985803,-2383812631451403108,-483691475304985229>(),
         var1,
         var2x -> {
            var2.accept(var2x);
            this.I();
         }
      );
   }

   private void j(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3s12ckkrioyij","X74Da+aW5HXY4jj2k1ewFeC8O7sW9ojNupVgb3JCiP4=",-3178416988885005941,-6653037593620570192,9173088468112489641,5199593911374185601>()) {
            case 642983850:
               if (var1 < com.yiyiaddon.e.f.b.c.values().length) {
                  this.f.a().a = com.yiyiaddon.e.f.b.c.values()[var1];
                  this.I();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s127m0m6ib8wg1","CvJL6Q7PjDw/x8sTEYU/XRTdbXG8HIREP/A9Z/zN2Zg=",-136363671573269709,-496476437663934419,-5491503222571108838,-112394174222723056>()) {
                     case 1748119808:
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

   private void k(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sx40b31ef2ww","6rTIknZg8YI553o9V1xiaT9X3PxFwqCLK33i0IBw4Dw=",8046394025203885796,4684441596807953994,1095279448670248629,2276792443714442222>()) {
            case 1321340123:
               if (var1 < com.yiyiaddon.e.f.a.a.b.values().length) {
                  this.f.a().a = com.yiyiaddon.e.f.a.a.b.values()[var1];
                  this.I();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s79l65fnvp3rj","kgzpanHr6gqCijGlj2T7OJK3mJTGSAN0w8p1STQXbM8=",8147742365454474785,-2178682989474417786,-1333843202662420944,-5904139981302874557>()) {
                     case -924374497:
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

   private void l(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s127c2is4gm11l","4wle6ZJ9LfNNXf59C8/m3SAU9yKuxv5X1WM2cN0A5fU=",-3518655874718563616,-5091364017730887414,-825560822184086454,7699155681168817586>()) {
            case -81933328:
               if (var1 < com.yiyiaddon.e.f.a.a.b.values().length) {
                  this.f.a().b = com.yiyiaddon.e.f.a.a.b.values()[var1];
                  this.I();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sl3eh6cwv7vvk","8WzHPklSc7JyD+jiYv/Q256y8AKMCtdw3naZQrbRgTs=",1046079426894491048,8184810842104098051,5042367320862962545,-4624561279725268549>()) {
                     case 1131118307:
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

   private void I() {
      com.yiyiaddon.d.b.e.d(this.f);
   }

   public static List<g.b> B() {
      if (ae == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tlcx0v76aq3h","lQ3km3htpd7+5UM1J1k8mpUBklONvaNqA+vowC7V56k=",2337798154384627133,6145342640130631899,-8671064671651684909,237941682301900253>()) {
            case 1263527224:
               LinkedHashMap var0 = new LinkedHashMap();
               Iterator var1 = ab.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s3lnvdxk5gqzob","bLCd32An1Zt9UXzhAnMWWyflcx/BNDvOQ/eC/4T7hKc=",-521739016011191866,-4108073690682504422,-7478637296830127961,6903254252756319572>()) {
                  case -1153769215:
                     while (var1.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2mmncot5kioy3","U0BOiidVExqZdlxmKgpb5SZvIdm6FuickZwAifyBDQo=",4635505413324899841,629500789045207347,4705319167615141338,-4649885467155566324>()) {
                           case 738565996:
                              String var2 = (String)var1.next();
                              var0.put(var2, new ArrayList());
                              switch ((int)com.yiyiaddon.m.b.a<"s7eyln563716b","c9vQMxUF4w9UnnEsR1kVcsnd5nDzw0RK3NXzjVfomcA=",1131786100710125962,-4638340790449233484,7612088961224552074,-4338705713084777043>()) {
                                 case -2015145675:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var1 = com.yiyiaddon.e.f.b.b.z().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s183eq1fyp58d2","Dzwbk9TsqE2uZy1rODsnAnp+PnI+A6bh1KbHLkCtMvo=",8019123417988003223,-8146355944052455992,-7688642148024313489,-6456683097790485625>()) {
                        case -1783737495:
                           while (var1.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s129qf1mizzutc","P5ljofHt1hc9CEpFCGon+pULqpKD9ewaTApL5p2qa0Q=",1432978581868684612,6610995911710753000,-5714668654023272005,4658188737397784910>()) {
                                 case 1877784536:
                                    EntityType var8 = (EntityType)var1.next();
                                    Identifier var3 = BuiltInRegistries.ENTITY_TYPE.getKey(var8);
                                    if (var3 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ztu40afhwkxe","PZt4D3LB2+Mwdery0iS963hjdhTDpDXrSoHWYRtHoBY=",5164392110449211562,358571597183170615,-5246358174999685001,-8442269760363672886>()) {
                                          case -102233508:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2umawmlc088x1","arkTYZYPVi6fl+ypQfVm39+dGBZWZTe+hz0b7xhLeec=",147595539917079058,-925711394802564661,-1672246594361611889,-6182841784213941923>()) {
                                                case -176790967:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       ((List)var0.get(a(var8))).add(new c.a(var3.toString(), var8));
                                       switch ((int)com.yiyiaddon.m.b.a<"s2hb8rau2hivls","VdrGOQyIsCTnubCbwJqphtbMSA0nI+g+ruvqFqqUP48=",8842492420075533775,-6227859397780617589,9098865296407999054,2458591555671134696>()) {
                                          case -260846813:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           Comparator var7 = Comparator.comparing(g.b::D, b).thenComparing(g.b::L);
                           ArrayList var9 = new ArrayList();
                           Iterator var10 = ab.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s26qlm7cl46hf1","jA6SJaE1dw6yjqtmV/IecGS8eXdXsi9z2eFN9EuXa7I=",408544656637946370,5351165361007678587,1715865713284886402,4270436612069813720>()) {
                              case 205512831:
                                 while (var10.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3b91emcm22t17","L16UlM4TfbOaaaxZJZhRXKkw6/z3oYOL0ZeGNPmRc+w=",-5644515150917315011,9069139862101204390,-8617719087497449648,-5658110777837021714>()) {
                                       case -1112560709:
                                          String var4 = (String)var10.next();
                                          List var5 = (List)var0.get(var4);
                                          var5.sort(var7);
                                          var9.addAll(var5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s358za4ur11peu","ZVPldKoDu+q9KlnjE6Qar8F9k96ZRFm32vBP5oHGqO8=",4087090124929000875,451711136342951735,295279920731716000,-8284955129461991037>()) {
                                             case -13573784:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 ae = List.copyOf(var9);
                                 switch ((int)com.yiyiaddon.m.b.a<"s26bflix0l8ep","F1gRkhLdcpTDotHeSpuYJgkwS4T4bvt+/pGFGbIl4R4=",180557243827131610,5952581629150815480,-4032673324597681364,1571110717203073844>()) {
                                    case -72988433:
                                       return ae;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return ae;
      }
   }

   private static String a(EntityType<?> var0) {
      if (var0 == EntityType.PLAYER) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fuse43fvy2pp","7WK3vQ2bSiMQeELP6Bv+w1N7Yt/SgkGUSCAWygmw5dE=",-5933731355463909087,7741681527725625443,6452742164472463502,-6667621858098739622>()) {
            case -935876329:
               return (String)com.yiyiaddon.m.b.a<"s3kd3cirit0gqy","yUzZahxt3W+yevhqz3e6IiKrkMimKyj5ubhuwCduhanVTyYLOSnYaE54vyg=",3697545775253548947,-4399031448475827226,8072339655762321437,2105153246730355901>();
            default:
               throw null;
         }
      } else {
         switch (var0.getCategory()) {
            case MONSTER:
               String var4 = (String)com.yiyiaddon.m.b.a<"s3751ukarc8gip","rlSbYmBpl5gb4lfU7s6H56rF31OCh+2xaoY1dEFAZ6e9CukU43UMWcAkEN4=",5253429920072393334,-8929868288803181357,-4579006123237392076,-7465955347706664596>();
               switch ((int)com.yiyiaddon.m.b.a<"s24xts78a666us","pjzM2RTG4zR9/qlv3lc8XSPMKq8IkU/EQyMH2bouxR0=",-5183392601499369246,4193189524228610345,8676208211475816923,-2415252277250179744>()) {
                  case -956291696:
                     return var4;
                  default:
                     throw null;
               }
            case CREATURE:
               String var3 = (String)com.yiyiaddon.m.b.a<"s34q7kljmzkrpx","L4de1N6wRxLTHVf+hJG5LpMhBB+2ZVY7jBNhoOxvdzkD1KK2wmILplyD364=",6982618420845136701,8154989457569930834,-2416721191367839565,560735871542901765>();
               switch ((int)com.yiyiaddon.m.b.a<"s2yklvzs5n3iet","gAB1l0kxI7g0qyFd/yFi2iF7biXVgY5GEGwffTSrwaw=",2559353663927271795,588939438164841669,-8863313755895350016,-4200909717828184500>()) {
                  case 1410036054:
                     return var3;
                  default:
                     throw null;
               }
            case AXOLOTLS:
            case WATER_CREATURE:
            case UNDERGROUND_WATER_CREATURE:
            case WATER_AMBIENT:
               String var2 = (String)com.yiyiaddon.m.b.a<"s135xulzp6sgt0","jVZSYBibU7JCVjxpxS+OW7AF4otNymLEGVZixCZJX/CME7W0SNu/Z0eqQAyYW7pY",6175247208359982705,-5714295255731504575,2318334083857819396,4270151058678274706>();
               switch ((int)com.yiyiaddon.m.b.a<"sj8k37ri3sqc2","kwNnz8R6nYczlnufA0Ll7QFwpkHdTi2Bz//zjREGJZI=",-6813337372807546970,6443200401752242224,7683365463447981445,-417175555145782881>()) {
                  case -1765473710:
                     return var2;
                  default:
                     throw null;
               }
            case AMBIENT:
               String var1 = (String)com.yiyiaddon.m.b.a<"s3mz5pv1mobcfr","aheJlTyv64oNRRObwQ9epFv/jfJD5DCUQaLecuG01hDrNyeWY6QM8UWjp5NO367M",9113651820524221117,-6421196814328719634,-440961856044544547,8671905076172857598>();
               switch ((int)com.yiyiaddon.m.b.a<"s31o29rrpto6l0","bSNAphteT+OG0q4fKE5wKv3cjLXL+9CPdXR8KNZD8wE=",8906229199013037543,-936685994609668979,-8111462156250945478,9092423564144249781>()) {
                  case -8920629:
                     return var1;
                  default:
                     throw null;
               }
            case MISC:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s3nch7bjybr74b","sJ5nWhxNe9zQCUQi1eC0IguXterxcA81BeE6jwKgU2i0Z05rjmZ2R/TdPCw=",1180507474782769242,5226752020223152418,852331556095102365,8756274000232971277>();
               switch ((int)com.yiyiaddon.m.b.a<"s3m2w7lhsu5kwh","xMZHTe5ABfoQ70eCKgtxIermA3F56p9lCm1kfbDlT4o=",8944298305118141358,2460775748415235481,2703917901058475332,4927254544888258092>()) {
                  case -1633024334:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }
      }
   }

   public static int K() {
      return B().size();
   }

   public static String R(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      EntityType var10000;
      if (var1 == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s2gtq7m9lnpg3y","SV4A91vaCHNSLRIir4wkRDhwAf9t2WHWqlD0KXS8QR8=",8531988149146955657,-4900956675161748592,233110103475198289,7635700159332554417>()) {
            case 1077964452:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s9ijbspka8a3l","yH07gU663pwy5rIs0LQhHd6GpJ6zz9v+XnF3kp3/aTQ=",-8976102579445753451,-2833194503299025910,7024746118199362079,8924187156797785284>()) {
                  case 644066321:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = BuiltInRegistries.ENTITY_TYPE.getValue(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1tp9aiuwyglqh","MWdeGuZqIOlVNIE40WX+lfmSf/2bE1y/flIoU/wcv5E=",2868884757267259912,7846204975138616182,5306587265807841842,-5535940793348089551>()) {
            case 1722958364:
               break;
            default:
               throw null;
         }
      }

      EntityType var2 = var10000;
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s36jkp4jqbo7qo","AOcPguadYC8zN9TpqSZps5bNkGO7FqjJ/f0Oa7q03xQ=",-8190119403055982248,5949361226119324656,8670392072220510189,1099781454059169987>()) {
            case 871922309:
               switch ((int)com.yiyiaddon.m.b.a<"siddmu4vzlh08","c73cESAANSZJzw0zK1QveFOAE0teZzip0PhlszY3lug=",-5338423836912286683,5915860865140251832,6839212764267638707,-5343816520671323445>()) {
                  case -586044253:
                     return var0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var3 = var2.getDescription().getString();
         switch ((int)com.yiyiaddon.m.b.a<"s11tkl3kh6eaq2","t6kCYv2c4JwifDoYWD+9Xg5dsp1ZI5iSuPIzKoW38qM=",6029757290056431738,1429609001979391029,897451847865933970,-6220151806901099268>()) {
            case -476609746:
               return var3;
            default:
               throw null;
         }
      }
   }

   private String az() {
      List var1 = this.f.a().U;
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3e4fn1sjsma7o","6OeZURUHFI1yVpr2FyyuhTynQ/fEMKe7sHdUOdhxaMs=",-6818776703331153641,1818224316665258271,-1770945657955110046,7606299336656435527>()) {
            case -865784990:
               return K() + "";
            default:
               throw null;
         }
      } else {
         return "" + var1.size() + K();
      }
   }

   private void by() {
      if (this.e.a() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v71pcu9sqgkm","aN7HFb4DKP9rLki4K/2qJzLmK40xLdMIz0oZMH+N+mU=",-9188127375744285623,3834432650764996133,-4638661307474094932,5534490873171853023>()) {
            case -1580277855:
               return;
            default:
               throw null;
         }
      } else {
         List var1 = this.f.a().U;
         this.e
            .a()
            .setScreen(
               new g(
                  (String)com.yiyiaddon.m.b.a<"s2b2yu31w204eo","fNDVlNWi/J2Vmdp8bJ6NBEBCVzgGIyMyVtuL7+vitVTgWlc2",-5669957250053892338,-4257036475878217502,1323425581117183631,-7446991948325530646>(),
                  this.e.a().screen,
                  B(),
                  () -> new ArrayList<>(var1),
                  var1x -> this.e(var1x, true),
                  var1x -> this.e(var1x, false)
               )
            );
      }
   }

   private void bz() {
      if (this.f.a().U.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s33qyoyl8t0uyc","n6oeOy3FI8dWZQaE1GqNFMBo9VX4MkMF+LJAPtJtrLk=",-1792793353320027827,8135610697547565817,-6203103849087309363,3008292221537127212>()) {
            case 1199088912:
               return;
            default:
               throw null;
         }
      } else {
         this.f.a().U.clear();
         this.I();
      }
   }

   private void e(String var1, boolean var2) {
      List var3 = this.f.a().U;
      boolean var10000;
      if (var2) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s2sfkqxw5jsv1m","kyIov9JtzZblTM1ocn3exuwa0FuBt8XJmQ/cn62rUeY=",-3200826946114140083,3693714173591604147,-2998119764158686813,8143356921828310039>()) {
            case -301986239:
               if (!var3.contains(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1bbfvjh5px3b0","pipGfugkVhXA4brUWBczKXQcnHJSmJOuItGnL+00h8c=",4919936463580538,-7931451970594695527,-7288322729511886867,8388899220338526865>()) {
                     case -1589231020:
                        if (var3.add(var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"spuyoal33cfnc","wDojZS+2sRlJJuizv1+fgGhZtfFPESEpJdMCjv97vcY=",4337088342489834290,-5724700518975277057,-3327982318972223653,-726843067350297542>()) {
                              case 2129205080:
                                 var10000 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3fmxe7tnv8maq","XdHeue2MVQ8ihR4E4w2IJOWkZ5VyVUkHRiiMZsWlBLc=",949788614495926127,-4914291896072644213,-7758131012215450310,-1493623493797408845>()) {
                                    case 1083819960:
                                       break label35;
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

               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s2umjl98y7oz4y","xfAKKVpTLxWGOQwwHU95/eLGf3sRtU+DejhvaQrfeog=",-1124971583004763387,-6249255004005567582,-6798024265660629003,-546235110894670387>()) {
                  case 2020975527:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var3.remove(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s2569kt2lc9wts","vhZc4zZKLsu8HfWAExVFOPYFZJ2wT7duDFO2eE0foZE=",3124933751492021381,-5825572767279226745,371677102212021629,-2738828987169355535>()) {
            case -1823919825:
               break;
            default:
               throw null;
         }
      }

      boolean var4 = var10000;
      if (!var4) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nf67a60ex583","LDwF6Rt/ouCQB2aK40l9PVaY8h7crqny2TbeJE5PX7Y=",7339682754044851215,-4360859454817298670,-3489602584459435866,-969297624215560217>()) {
            case 2041224369:
               return;
            default:
               throw null;
         }
      } else {
         this.I();
      }
   }

   private static Item a(String var0, EntityType<?> var1) {
      Item var2 = w.get(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ix6ab3mbxm22","A4PlZJEBztonR6LUkgQPNeiAdu4wGy3uq2RAqnckAXI=",-3693676824673134876,5608931994277723619,5554153619933407896,-7924474872678177392>()) {
            case -71987689:
               return var2;
            default:
               throw null;
         }
      } else {
         Identifier var3 = Identifier.tryParse(var0);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3p3kcn7pb2icr","LAhoydvC4ej+47NmCSawD+glyRSVFZ+Cp1LRZNDHXPQ=",7165091215096513660,7151889171236899179,-2123724224616269931,9014072315344108493>()) {
               case 917717283:
                  return null;
               default:
                  throw null;
            }
         } else {
            Item var4 = BuiltInRegistries.ITEM.getValue(var3);
            if (var4 != null) {
               label32:
               switch ((int)com.yiyiaddon.m.b.a<"sjw08enl51yna","z5l1U9FT+ykWN+3XGFFd7qj++Kr9NOP8vcKr7QKn5dE=",-1603186514220030663,3065247740514663352,1761751238524137992,-4327285415835454901>()) {
                  case -84752922:
                     if (var4 != Items.AIR) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1joa39jq0cgk7","EGbbxR4cZsLtVtzBRYnR+vyaPP8HAiYAB7TvaqnP8t8=",5505833768174218053,-7514347061232543779,1548153239495739491,-6989504742670278032>()) {
                           case -215538779:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3t12hk44sn5lx","EkYC/cyrVPRmuF29Ek5ZXSqQ5S3SuqBPlDfUnguyN9U=",-6173239080837636142,-93585941503997585,890823812323524279,2527350230455691863>()) {
                        case 472386614:
                           break label32;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"sz7sqcwwfrc3y","0+mSBTXviP3i9W4QZv/JgUPC2kaNR8fuSIhMO7CUqtA=",3496565397237789941,4690867716577143286,-2601399427322181687,-8780493696918061833>()) {
               case -1074232058:
                  return null;
               default:
                  throw null;
            }
         }
      }
   }

   private static final class a implements g.b {
      private final String jf;
      private final EntityType<?> a;
      private final String jg;
      private final Item d;
      private ItemStack b;

      private a(String var1, EntityType<?> var2) {
         this.jf = var1;
         this.a = var2;
         this.jg = com.yiyiaddon.e.f.c.a.c.a(var2);
         this.d = com.yiyiaddon.e.f.c.a.c.a(var1, var2);
      }

      @Override
      public String L() {
         return this.jf;
      }

      @Override
      public String D() {
         return this.a.getDescription().getString();
      }

      @Override
      public String B() {
         return this.jf;
      }

      @Override
      public String M() {
         return this.jg;
      }

      @Override
      public EntityType<?> a() {
         return this.a;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         com.yiyiaddon.l.g.c var5 = com.yiyiaddon.l.g.c.a();
         if (this.a.getCategory() != MobCategory.MISC) {
            switch ((int)com.yiyiaddon.m.b.a<"s2bxzix5uys0or","SzRDUZOkWAQ+ldi39Oyno8f3Hue9FGRzfir03/1yIuw=",-4923907532193225402,-235097702003821825,-8021927838581296085,8263466510627733573>()) {
               case 1647869908:
                  if (var5.b(var1, this.a, var2, var3, var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3qxa80vtkroh8","yV2odsF4WRA8GRwZ78ZyIV0ayKOD6fgcqyn/d/+LRRE=",-1485773226624247496,-7333623569693817084,6270930116689219512,5049447258746720163>()) {
                        case 1247989642:
                           return true;
                        default:
                           throw null;
                     }
                  }

                  if (!var5.b(this.a)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2txuqs2djwon4","iXW9IOOukgu/ABLe6Que+8OzgBKxsZwaih5lSucBQ3s=",-5083477813183035948,164803245973446264,8160551048603478822,-7535046049901592404>()) {
                        case 505961353:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var5.a(var1, this.a, var2, var3, var4)) {
            switch ((int)com.yiyiaddon.m.b.a<"s10yf3fodim1cv","3l2s9o6lpl2NGzy+14xkDXsZBeFqJag6/aOqeDIwpL8=",-8492520835609753940,-5398446514080676249,2308979198954454669,-7953061697163386489>()) {
               case 1277734550:
                  return true;
               default:
                  throw null;
            }
         } else {
            if (this.d != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s34c952gic8yn3","Sc5ByJTk/MatWj+qjGFxpMhw2Uuk0GuipXKS3CraCdM=",-4901557609428551319,3507541675719610853,-5516472721698096867,8751433742921316927>()) {
                  case 757957005:
                     if (this.b == null) {
                        label40:
                        switch ((int)com.yiyiaddon.m.b.a<"s21599m9ki71z1","HFMc5z7ajvJRvDZ+OF7N+SUNmE81cODClknlyt0v9Ak=",-280467983355015895,-3710274581863835360,-2414790496119335841,5767787007923390191>()) {
                           case 195232998:
                              this.b = new ItemStack(this.d);
                              switch ((int)com.yiyiaddon.m.b.a<"s2wc08tfklaslx","c1lV6hb2CIhTp7N8zsQublDdrGuh8Swhd3zrDk6e+V8=",-6181868426205685203,-5229818247094024062,-9088554647076632768,3465378158060738629>()) {
                                 case -394021805:
                                    break label40;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (var5.a(var1, this.b, var2, var3, var4)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2s4ttfvazs5gy","wlNZToGHkJfM5dvjImyK8pEh+tVqA9No6lxI4H2oFx0=",-8990506581402043949,587118252810038733,8950881357774540165,3218852033122895933>()) {
                           case 2118621570:
                              return true;
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            this.c(var1, var2, var3, var4);
            return true;
         }
      }

      private void c(Canvas var1, float var2, float var3, float var4) {
         com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
         j.a(var1, var2, var3, var4, var4, var4 * 0.24F, var5.uQ, 1.0F);
         j.c(var1, var2, var3, var4, var4, var4 * 0.24F, var5.uX, 1.0F, 0.18F);
         String var6 = this.D();
         if (var6.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s34a1n5idmqxch","7+DqnB/DpSjpQ2NbON9TEWttcWWCt1C0EhhPuT7aHIE=",-8997066345833705877,-1275135440091381769,-5588316362643894118,3912082206442251745>()) {
               case 19301630:
                  return;
               default:
                  throw null;
            }
         } else {
            String var7 = new String(Character.toChars(var6.codePointAt(0)));
            float var8 = var4 * 0.5F;
            float var9 = com.yiyiaddon.l.g.a.b(var7, var8);
            com.yiyiaddon.l.g.a.b(var1, var7, var2 + (var4 - var9) / 2.0F, com.yiyiaddon.l.b.d.c(var3 + var4 / 2.0F, var8), var8, j.a(var5.uU, 1.0F));
         }
      }
   }
}
