package oxyac.shopping.data;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import oxyac.shopping.data.parsers.AbstractCarParser;

@Service
public class AsyncThread {

    @Async
    public void startParsing(AbstractCarParser carParser){
        carParser.parse();
    }
}
