package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REWARD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_REWARD, PREFIX_TAG);
        Phone phoneIdentifier = null;
        Email emailIdentifier = null;

        try {
            if (argMultimap.getPhoneIdentifier()) {
                phoneIdentifier = ParserUtil.parsePhone(argMultimap.getAllValues(PREFIX_PHONE).get(0));
            } else if (argMultimap.getEmailIdentifier()) {
                emailIdentifier = ParserUtil.parseEmail(argMultimap.getAllValues(PREFIX_EMAIL).get(0));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (!argMultimap.getAllValues(PREFIX_PHONE).isEmpty()) {
            if (argMultimap.getAllValues(PREFIX_PHONE).size() == 1) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getAllValues(PREFIX_PHONE).get(0)));
            } else if (argMultimap.getAllValues(PREFIX_PHONE).size() == 2) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getAllValues(PREFIX_PHONE).get(1)));
            }
        }
        if (!argMultimap.getAllValues(PREFIX_EMAIL).isEmpty()) {
            if (argMultimap.getAllValues(PREFIX_EMAIL).size() == 1) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getAllValues(PREFIX_EMAIL).get(0)));
            } else if (argMultimap.getAllValues(PREFIX_EMAIL).size() == 2) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getAllValues(PREFIX_EMAIL).get(1)));
            }
        }
        if (argMultimap.getValue(PREFIX_REWARD).isPresent()) {
            editPersonDescriptor.setReward(ParserUtil.parseReward(argMultimap.getValue(PREFIX_REWARD).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return argMultimap.getPhoneIdentifier()
                ? new EditCommand(phoneIdentifier, editPersonDescriptor)
                : new EditCommand(emailIdentifier, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
