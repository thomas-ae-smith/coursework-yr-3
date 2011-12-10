package EPrints::Plugin::Export::MyPlugins::Wordle;

@ISA = ( "EPrints::Plugin::Export" );

use strict;

# Wordle Summary:
# Collate the abstracts of all of the selected documents
# and submit to the Wordle web-service for creating word-clouds

# plugin returns an auto-submitting hidden html form
sub new
{
	my( $class, %opts ) = @_;

	my $self = $class->SUPER::new( %opts );

	$self->{name} = "Wordle Summary";
	$self->{accept} = [ 'dataobj/eprint', 'list/eprint' ];
	$self->{visible} = "all";
	$self->{suffix} = '.htm';
	$self->{mimetype} = 'text/html; charset=utf-8';
	
	return $self;
}

# sanely handle deciding whether to print or return output
sub output_helper
{
        my( $plugin, $r, $output, %opts ) = @_;
        if (defined $opts{fh})
        {
                print {$opts{fh}} $output;
        }
        else
        {
                push @{$r}, $output;
        }
}

# return the abstract of the document reinforced with the keywords
sub output_dataobj
{
	my( $plugin, $dataobj ) = @_;

	return $dataobj->get_value('keywords')."\n".$dataobj->get_value('abstract')."\n";
}

# return a page containing an auto-submitting hidden html form
# populate the textarea with the abstracts
sub output_list
{
	my ($plugin, %opts) = @_;

        my $r = [];

	# set up header and footer for template
        my $header = <<END;
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
                <head>
                        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
                        <title>Wordle Export Plugin</title>
                </head>
        	<body onload="document.forms['hiddenform'].submit();">
			<form id="hiddenform" action="http://www.wordle.net/advanced" method="POST">
   			<textarea name="text" style="display:none">
END
        my $footer = <<END;
                        </textarea>
                	loading...
                	</form>
        	</body>
	</html>
END
	# template header output
	$plugin->output_helper($r, $header, %opts);
	# populate with words from documents
        foreach my $dataobj ($opts{list}->get_records)
        {
                my $part = $plugin->output_dataobj($dataobj, %opts);
   		$plugin->output_helper($r, $part, %opts);
        }
	# template footer output
        $plugin->output_helper($r, $footer, %opts);

	# final decision to return or print output
        if (defined $opts{fh})
        {
                return undef;
        }
        return join('', @{$r});
}

1;
