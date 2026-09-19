package com.yiyiaddon.e.n;

import com.yiyiaddon.k.e.e;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public final class a {
   private a() {
   }

   public static String bT() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.hasSingleplayerServer()) {
         switch ((int)com.yiyiaddon.m.b.a<"s34cynocwjpgc3","Jy3zDWoqH+7X8thNvW4RrDevV2/ev0M+3ji7dgmdJNg=",608640734798568600,-681944289566278726,7327038864823702620,5071464419860046654>()) {
            case 380867752:
               return (String)com.yiyiaddon.m.b.a<"s26bqdz0qf2em1","G0JqENqKClYnUz3XTH22pjRVy9HsZ1NA1z9KPb5ESmmdSn9Lf7m0meiiXgBt8d/KCgUJmg==",1787233727724622135,4665592194598173006,-6245681081287852523,-3602221314773328308>();
            default:
               throw null;
         }
      } else {
         String var1 = e.gA();
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ufqpaxm4im0i","nOyOD1DWWEn8TeOs0Na68MZYLqYh6WVS2ewRHV8JCRk=",44779547995861502,2570967502903355915,6872119932396667378,-97925930463574922>()) {
               case 1369276618:
                  if (!var1.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2rzrpl1qnuu77","wUpO4oijXQ5JrNHujzVqDwGUtaryetBRl3bdUntuJOE=",7920294874277860894,-4715983909853289854,-6383490304166463461,-6901063685912499701>()) {
                        case 775014479:
                           return var1;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return cV();
      }
   }

   public static String bU() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19n5oe6d9vnct","941F3bYOsKTipiNhofzSBefpdxeNYvXELL4Rc5DNqPQ=",1733670270749845752,-7242672423832670398,2227948177110770992,-1395730680275251803>()) {
            case -53803784:
               switch ((int)com.yiyiaddon.m.b.a<"s12ejuuja8ven8","+kiRO5oxlPWfkZrOMjIPhL834b11J55Eaqb8W2F3bB0=",6959962904684956953,-4944431615241678902,-1522386505359915509,4120688016103325424>()) {
                  case -2019518660:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.level.dimension().identifier().toString();
         switch ((int)com.yiyiaddon.m.b.a<"s3e80rw6jep5u6","fPm8Rs7ArEAMGc3yJffBwBIkkqaUAJwpmJsSOdAucQ0=",-7126855829500973298,-2211501563857112127,-1503922442763165255,4140405034478856203>()) {
            case 1961295420:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static boolean cH() {
      return Minecraft.getInstance().hasSingleplayerServer();
   }

   public static String cV() {
      Minecraft var0 = Minecraft.getInstance();
      ClientPacketListener var1 = var0.getConnection();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"su3vieqpuaw93","To40N33D28hgJyuQPrKpgGE15t8ah76uYzpLMaojQFA=",-4457776929638535445,4302887168560652113,5394288007452200713,-1726798301175870445>()) {
            case 84529376:
               if (var1.getConnection() != null) {
                  SocketAddress var2 = var1.getConnection().getRemoteAddress();
                  if (var2 instanceof InetSocketAddress) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3gwkkm0zq6kir","eXariwqqzwzGLKSP3zUTTIl5nfe41WAmQXSRjojQzl0=",5616493423226474957,287958537538725017,6298529671205440050,-3236846702951554000>()) {
                        case -1627533084:
                           InetSocketAddress var3 = (InetSocketAddress)var2;
                           String var10000;
                           if (var3.getAddress() != null) {
                              label26:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ra4yedqfptui","74MQrdDsIegvZY8/5ft2jQL0QEYziMWvAI6XLZZevvc=",9056006372127111117,7694961221937928887,4822052610640022931,-504891196458285514>()) {
                                 case -587279896:
                                    var10000 = var3.getAddress().getHostAddress();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2wnjf3ntmvgk1","b2JcvpyL+ffK/3/dSbJKy7keKDSfKQmiGgUJqEs7EDA=",-4610490606925279023,-8185376326364608688,2957358448265442167,-6284136349412604321>()) {
                                       case -1813920951:
                                          break label26;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = var3.getHostString();
                              switch ((int)com.yiyiaddon.m.b.a<"szsral7ticuv3","OPP4Tp1COkq8COfDF7IX6JD6+FmQcAZgLdMXAVuaNzg=",-3167096917816904727,8907623166902156657,-1612919672185228042,-3824120830803194527>()) {
                                 case 1098651640:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var4 = var10000;
                           return (var4 + var3.getPort()).toLowerCase(Locale.ROOT);
                        default:
                           throw null;
                     }
                  }

                  return (String)com.yiyiaddon.m.b.a<"s1advidyo4e4gk","7TSjCEZntabmyXPD8MhyAACSNpomPjaWWgDw62yveESyth9g8ZAGd+eb",8298462081695349391,-6934824550236767189,-5702449855658330664,4099898564156316159>();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sf4anl5qo1can","SkJ1kPos+rew4pDYpRES/Jl1POuv03uv1N0qFHeujbE=",-1650184551815888413,-7628988120414232134,-5412993247714708373,3767245907681834006>()) {
                     case -1278952511:
                        return (String)com.yiyiaddon.m.b.a<"s1advidyo4e4gk","7TSjCEZntabmyXPD8MhyAACSNpomPjaWWgDw62yveESyth9g8ZAGd+eb",8298462081695349391,-6934824550236767189,-5702449855658330664,4099898564156316159>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s1advidyo4e4gk","7TSjCEZntabmyXPD8MhyAACSNpomPjaWWgDw62yveESyth9g8ZAGd+eb",8298462081695349391,-6934824550236767189,-5702449855658330664,4099898564156316159>();
      }
   }
}
