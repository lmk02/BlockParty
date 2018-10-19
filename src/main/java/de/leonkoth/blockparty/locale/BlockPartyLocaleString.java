package de.leonkoth.blockparty.locale;

import de.pauhull.utils.locale.storage.LocaleSection;
import de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.command.CommandSender;

public class BlockPartyLocaleString extends LocaleString {

    public BlockPartyLocaleString(LocaleSection section, String... defaultValue) {
        super(section, defaultValue);
    }

    public void message(boolean prefix, CommandSender sender, String... placeholders) {
        if (prefix) super.withPrefix(BlockPartyLocale.PREFIX);
        super.message(sender, placeholders);
    }

    @Override
    public void message(CommandSender sender, String... placeholders) {
        message(true, sender, placeholders);
    }

    @Deprecated
    @Override
    public LocaleString withPrefix(LocaleString prefix) {
        return super.withPrefix(prefix);
    }

}
