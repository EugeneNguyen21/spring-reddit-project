package ducnguyen.springredditproject.service;

import ducnguyen.springredditproject.mapper.SubredditMapper;


import ducnguyen.springredditproject.dto.SubredditDto;
import ducnguyen.springredditproject.exceptions.SpringRedditException;
import ducnguyen.springredditproject.model.Subreddit;
import ducnguyen.springredditproject.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;


    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.mapDtoToSubreddit(subredditDto);
        Subreddit save = subredditRepository.save(subreddit);
        subredditDto.setId(save.getId());
        return subredditDto;

    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long subreddit_id) {
        Subreddit subreddit = subredditRepository.findById(subreddit_id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + subreddit_id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    public boolean isSubredditExisted(String subredditName){
        List<Subreddit> allSubreddits = subredditRepository.findAll();
        for (Subreddit subreddit: allSubreddits) {
            if(subreddit.getName().equals(subredditName)){
                return true;
            }
        }
        return false;
    }

}
